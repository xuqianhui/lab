/*
 * Copyright 2016 Su Baochen and individual contributors by the 
 * @authors tag. See the copyright.txt in the distribution for
 * a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.edu.sdut.softlab.controller;

import cn.edu.sdut.softlab.model.Stuff;
import cn.edu.sdut.softlab.service.StuffFacade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import javax.transaction.UserTransaction;

@Named("userManager")
@RequestScoped
public class StuffManagerImpl implements StuffManager {

  @Inject
  private transient Logger logger;
  @Inject
  StuffFacade userService;
  @Inject
  Credentials credentials;
  @Inject
  private UserTransaction utx;

  private Stuff newStuff = new Stuff();
  private String name;
  private String email;
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Stuff getNewStuff() {
    return newStuff;
  }

  public void setNewStuff(Stuff newStuff) {
    this.newStuff = newStuff;
  }

  @Override
  @Produces
  @Named
  @RequestScoped
  public List<Stuff> getStuffs() throws Exception {
    try {
      utx.begin();
      return userService.findAll();
    } finally {
      utx.commit();
    }
  }

  @Override
  public String addStuff() throws Exception {
    try {
      utx.begin();
      userService.create(newStuff);
      logger.log(Level.INFO, "Added {0}", newStuff);
      return "/users.xhtml?faces-redirect=true";
    } finally {
      utx.commit();
    }
  }

  @Override
  public String removeStuff() throws Exception {
    Stuff currentuser = userService.findByUsername(credentials.getUsername());
    if (currentuser != null) {
      try {
        utx.begin();
        userService.remove(currentuser);
        logger.log(Level.INFO, "Added {0}", newStuff);
        return "/users.xhtml?faces-redirect=true";
      } finally {
        utx.commit();
      }
    } else {
      return "/error.xhtml?faces-redirect=true";
    }
  }

  @Override
  public String updateStuff() throws Exception {
    Stuff currentuser = userService.findByUsername(credentials.getUsername());
    if (currentuser != null) {
      currentuser.setUsername(name);
      currentuser.setEmail(email);
      currentuser.setPassword(password);
      try {
        utx.begin();
        userService.edit(currentuser);
        logger.log(Level.INFO, "Added {0}", newStuff);
        return "/users.xhtml?faces-redirect=true";
      } finally {
        utx.commit();
      }
    } else {
      return "/error.xhtml?faces-redirect=true";
    }
  }
  
  @Override
  public String searchStuff() throws Exception {
    String s = credentials.getUsername();
    try {
      utx.begin();
      userService.findByNameLike(s);
      logger.log(Level.INFO, "Added {0}", newStuff);
      return "/query.xhtml?faces-redirect=true";
    } finally {
      utx.commit();
    }
  }
  
  public List<Stuff> getSearchStuffs(){
    String s = credentials.getUsername();
   
    return userService.findByNameLike(s);
  }

}