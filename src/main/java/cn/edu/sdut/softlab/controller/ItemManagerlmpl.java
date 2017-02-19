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

@Named("itemManager")
@RequestScoped
public class ItemManagerImpl implements ItemManager {

  @Inject
  private transient Logger logger;
  @Inject
  ItemFacade itemService;
  @Inject
  Credentials credentials;
  @Inject
  private ItemTransaction utx;

  private Stuff newItem = new Stuff();
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

}
