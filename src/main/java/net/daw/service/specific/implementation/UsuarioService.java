/*
 * Copyright (c) 2015 by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com)
 * 
 * openAUSIAS: The stunning micro-library that helps you to develop easily 
 *             AJAX web applications by using Java and jQuery
 * openAUSIAS is distributed under the MIT License (MIT)
 * Sources at https://github.com/rafaelaznar/openAUSIAS
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.daw.service.specific.implementation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Random;
import net.daw.service.generic.implementation.TableServiceGenImpl;
import javax.servlet.http.HttpServletRequest;
import net.daw.bean.specific.implementation.CompraBean;
import net.daw.bean.specific.implementation.UsuarioBean;
import net.daw.connection.implementation.BoneConnectionPoolImpl;
import net.daw.connection.publicinterface.ConnectionInterface;
import net.daw.dao.specific.implementation.CompraDao;
import net.daw.dao.specific.implementation.UsuarioDao;
import net.daw.helper.statics.ExceptionBooster;
import net.daw.helper.statics.JsonMessage;

public class UsuarioService extends TableServiceGenImpl {

    public UsuarioService(HttpServletRequest request) {
        super(request);
    }

    
    // Metodo para el login
    
    public String login() throws SQLException, Exception {
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        String strAnswer = null;
        String strCode = "200";
        if (oUserBean == null) {
            String login = oRequest.getParameter("login");
            String pass = oRequest.getParameter("pass");
            if (!login.equals("") && !pass.equals("")) {
                ConnectionInterface DataConnectionSource = null;
                Connection oConnection = null;
                try {
                    DataConnectionSource = new BoneConnectionPoolImpl();
                    oConnection = DataConnectionSource.newConnection();
                    UsuarioBean oUsuario = new UsuarioBean();
                    oUsuario.setLogin(login);
                    oUsuario.setPassword(pass);
                    UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
                    oUsuario = oUsuarioDao.getFromLogin(oUsuario);

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(oUsuario.getFnac());
                    Integer m = calendar.get(Calendar.MONTH);
                    Integer month = m + 1;

                    String mes = "";
                    if (month < 10) {
                        mes = "0" + month;
                    } else {
                        mes = Integer.toString(month);
                    }

                    String passFecha = oUsuario.getPassword() + mes;
                    if (pass == null ? passFecha == null : pass.equals(passFecha)) {
                        oRequest.getSession().setAttribute("userBean", oUsuario);
                        strCode = "200";
                        strAnswer = "Todo va dpm tío";
                    } else {
                        strCode = "403";
                        strAnswer = "User or password incorrect";
                    }
                } catch (Exception ex) {
                    ExceptionBooster.boost(new Exception(this.getClass().getName() + ":login ERROR " + ex.toString()));
                } finally {
                    if (oConnection != null) {
                        oConnection.close();
                    }
                    if (DataConnectionSource != null) {
                        DataConnectionSource.disposeConnection();
                    }
                }
            }
        } else {
            strAnswer = "Already logged in";
        }
        return JsonMessage.getJsonMsg(strCode, strAnswer);
    }

    public String logout() {
        oRequest.getSession().invalidate(); //Con el invalidate() terminas con la sesión.
        return JsonMessage.getJsonMsg("200", "¿Ya te vas tío?");
    }

    
    
        // Metodo para checkear la sesion, que usuario hay en ella
    
    public String check() {
        //String strAnswer = null;
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        String retorno = "";
        if (oUserBean == null) {
            retorno = "{\"status\":\"KO\"}";
        } else {
            retorno = "{\"status\":\"OK\",";
            retorno += "\"id\":" + oUserBean.getId() + ",";
            retorno += "\"nombrecompleto\":\"" + oUserBean.getNombre() + " " + oUserBean.getApe1() + " " + oUserBean.getApe2() + "\",";
            
            
            
            
            
//            ConnectionInterface DataConnectionSource = null;
//            Connection oConnection = null;
//            try {
//                DataConnectionSource = new BoneConnectionPoolImpl();
//                oConnection = DataConnectionSource.newConnection();
//                
//                UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
//                
////                retorno += "\"tipos\":\"" + oUsuarioDao.getTipoProductos(oUserBean.getId()) + "\"";
//                
//            } catch (Exception ex) {
//                ExceptionBooster.boost(new Exception(this.getClass().getName() + ":login ERROR " + ex.toString()));
//            } finally {
//                if (oConnection != null) {
//                    oConnection.close();
//                }
//                if (DataConnectionSource != null) {
//                    DataConnectionSource.disposeConnection();
//                }
//            }
            retorno += "}";
        }
        return retorno;
    }

    // Metodo para el cambio de usuario mediante su id
    
    
    public String change() throws SQLException, Exception {
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
        String retorno = "";

        if (oUserBean == null) {
            retorno = "{\"status\":\"KO\"}";
        } else {
            Integer id = Integer.parseInt(oRequest.getParameter("id_usuario"));
            ConnectionInterface DataConnectionSource = null;
            Connection oConnection = null;
            DataConnectionSource = new BoneConnectionPoolImpl();
            oConnection = DataConnectionSource.newConnection();
            UsuarioBean oUsuario = new UsuarioBean();
            oUsuario.setId(id);
            UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
            oUsuario = oUsuarioDao.get(oUsuario, 1);
            oRequest.getSession().setAttribute("userBean", oUsuario);
            retorno = "{\"status\":\"Se ha cambiado el usuario.\"}";
        }
        return retorno;
    }

    
    
        // Metodo para hacer la compra mediante el id del producto y su cantidad

    
    public String buy() throws SQLException, Exception {

        String retorno = "";
        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");

        if (oUserBean == null) { //Si no hay sesion...
            retorno = "{\"status\":\"KO\"}";
        } else {

            Integer idProducto = Integer.parseInt(oRequest.getParameter("id_producto"));
            Integer cantidad = Integer.parseInt(oRequest.getParameter("cantidad"));

            ConnectionInterface DataConnectionSource = null;
            Connection oConnection = null;
            DataConnectionSource = new BoneConnectionPoolImpl();
            oConnection = DataConnectionSource.newConnection();
            CompraBean oCompra = new CompraBean();
            oCompra.setId_producto(idProducto);
            oCompra.setCantidad(cantidad);
            oCompra.setId_usuario(oUserBean.getId()); //ya está el pojo lleno
            CompraDao oCompraDao = new CompraDao(oConnection);
            oCompraDao.set(oCompra);
            retorno = "{\"status\":\"OK\"}";
        }

        return retorno;

    }
    
    // Metodo para el login con numeros
    
//    public String loginnum() throws SQLException, Exception {
//        UsuarioBean oUserBean = (UsuarioBean) oRequest.getSession().getAttribute("userBean");
//        String strAnswer = null;
//        String strCode = "200";
//        if (oUserBean == null) {
//            
//            Integer num1 = 2;
//            Integer num2 = 3;
//            Integer suma = num1 + num2;
//            
//            
//            if (num1 == num2) {
//                
//                ConnectionInterface DataConnectionSource = null;
//                Connection oConnection = null;
//                try {
//                    DataConnectionSource = new BoneConnectionPoolImpl();
//                    oConnection = DataConnectionSource.newConnection();
//                    UsuarioBean oUsuario = new UsuarioBean();
//                    oUsuario.setLogin(login);
//                    oUsuario.setPassword(pass);
//                    UsuarioDao oUsuarioDao = new UsuarioDao(oConnection);
//                    oUsuario = oUsuarioDao.getFromLogin(oUsuario);
//
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(oUsuario.getFnac());
//                    Integer m = calendar.get(Calendar.MONTH);
//                    Integer month = m + 1;
//
//                    String mes = "";
//                    if (month < 10) {
//                        mes = "0" + month;
//                    } else {
//                        mes = Integer.toString(month);
//                    }
//
//                    String passFecha = oUsuario.getPassword() + mes;
//                    if (pass == null ? passFecha == null : pass.equals(passFecha)) {
//                        oRequest.getSession().setAttribute("userBean", oUsuario);
//                        strCode = "200";
//                        strAnswer = "Todo va dpm tío";
//                    } else {
//                        strCode = "403";
//                        strAnswer = "User or password incorrect";
//                    }
//                } catch (Exception ex) {
//                    ExceptionBooster.boost(new Exception(this.getClass().getName() + ":login ERROR " + ex.toString()));
//                } finally {
//                    if (oConnection != null) {
//                        oConnection.close();
//                    }
//                    if (DataConnectionSource != null) {
//                        DataConnectionSource.disposeConnection();
//                    }
//                }
//            }
//        } else {
//            strAnswer = "Already logged in";
//        }
//        return JsonMessage.getJsonMsg(strCode, strAnswer);
//    }

}
