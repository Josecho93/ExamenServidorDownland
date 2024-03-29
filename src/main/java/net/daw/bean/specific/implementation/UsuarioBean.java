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
package net.daw.bean.specific.implementation;

import net.daw.bean.generic.implementation.BeanGenImpl;
import net.daw.bean.publicinterface.BeanInterface;
import com.google.gson.annotations.Expose;
import java.util.Date;
import net.daw.bean.group.GroupBeanImpl;
import net.daw.helper.annotations.MethodMetaInformation;
import net.daw.helper.annotations.TableSourceMetaInformation;
import net.daw.helper.statics.MetaEnum;

@TableSourceMetaInformation(
        TableName = "usuario",
        Description = "Usuarios del sistema"
)
public class UsuarioBean extends BeanGenImpl implements BeanInterface {

    public UsuarioBean() {
        this.id = 0;
    }

    public UsuarioBean(Integer id) {
        this.id = id;
    }

    @Expose
    @MethodMetaInformation(
            IsId = true,
            UltraShortName = "Iden.",
            ShortName = "Identif.",
            Description = "Número Identificador",
            Type = MetaEnum.FieldType.Integer,
            DefaultValue = "0"
    )
    private Integer id;

    
        @Expose
    @MethodMetaInformation(
            UltraShortName = "Login",
            ShortName = "Login",
            Description = "Nombre de usuario",
            Type = MetaEnum.FieldType.String,
            IsForeignKeyDescriptor = true
    )
    private String nombre = "";
    
            @Expose
    @MethodMetaInformation(
            UltraShortName = "Login",
            ShortName = "Login",
            Description = "Nombre de usuario",
            Type = MetaEnum.FieldType.String,
            IsForeignKeyDescriptor = true
    )
    private String ape1 = "";
            
                    @Expose
    @MethodMetaInformation(
            UltraShortName = "Login",
            ShortName = "Login",
            Description = "Nombre de usuario",
            Type = MetaEnum.FieldType.String,
            IsForeignKeyDescriptor = true
    )
    private String ape2 = "";
    
            @Expose
    @MethodMetaInformation(
            UltraShortName = "Login",
            ShortName = "Login",
            Description = "Nombre de usuario",
            Type = MetaEnum.FieldType.Integer,
            IsForeignKeyDescriptor = true
    )
    private Integer sexo = 0;
    
    
            @Expose
    @MethodMetaInformation(
            UltraShortName = "Login",
            ShortName = "Login",
            Description = "Nombre de usuario",
            Type = MetaEnum.FieldType.Date,
            IsForeignKeyDescriptor = true
    )
    private Date fnac = new Date();
    
    
    
    @Expose
    @MethodMetaInformation(
            UltraShortName = "Login",
            ShortName = "Login",
            Description = "Nombre de usuario",
            Type = MetaEnum.FieldType.String,
            IsForeignKeyDescriptor = true
    )
    private String login = "";

    @Expose
    @MethodMetaInformation(
            UltraShortName = "Pass",
            ShortName = "Password",
            Description = "Frase de paso",
            Type = MetaEnum.FieldType.String
    )
    private String password = "";

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApe1() {
        return ape1;
    }

    public void setApe1(String ape1) {
        this.ape1 = ape1;
    }

    public String getApe2() {
        return ape2;
    }

    public void setApe2(String ape2) {
        this.ape2 = ape2;
    }

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public Date getFnac() {
        return fnac;
    }

    public void setFnac(Date fnac) {
        this.fnac = fnac;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
