package cn.edu.sdut.softlab.controller;

import cn.edu.sdut.softlab.model.Item;

import java.util.List;

public interface ItemManager {

  /**
   * 获得所有用户列表.
   *
   * @return 所有用户列表
   * @throws Exception
   */
  List<Item> getItems() throws Exception;

  /**
   * 新增物品.
   *
   * @return 返回物品列表页面
   * @throws Exception
   */
  String addItem() throws Exception;

  /**
   *删除物品.
   *
   * @return 返回物品列表页面
   * @throws Exception
   */
  String removeItem() throws Exception;
  
  /**
   *修改物品.
   *
   * @return 返回物品列表页面
   * @throws Exception
   */
  String updateItem() throws Exception;
}
