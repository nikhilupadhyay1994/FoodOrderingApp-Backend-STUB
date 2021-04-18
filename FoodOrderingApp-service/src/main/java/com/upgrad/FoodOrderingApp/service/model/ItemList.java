package com.upgrad.FoodOrderingApp.service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.UUID;


public class ItemList {

  private UUID id = null;
  private String itemName = null;
  private Integer price = null;

  public enum ItemTypeEnum {
    VEG("VEG"),

    NON_VEG("NON_VEG");

    private String value;

    ItemTypeEnum(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ItemTypeEnum fromValue(String text) {
      for (ItemTypeEnum b : ItemTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }

}
