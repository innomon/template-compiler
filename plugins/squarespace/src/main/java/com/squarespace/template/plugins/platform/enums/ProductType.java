/**
 * Copyright (c) 2015 SQUARESPACE, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.squarespace.template.plugins.platform.enums;

import static com.squarespace.template.GeneralUtils.getOrDefault;
import static com.squarespace.template.plugins.platform.enums.EnumUtils.codeMap;

import java.util.Map;


/**
 * This mirrors the Commons enum of the same name.
 */
public enum ProductType implements PlatformEnum {

  UNDEFINED(-1, "undefined"),
  PHYSICAL(1, "physical"),
  DIGITAL(2, "digital"),
  SERVICE(3, "service"),
  GIFT_CARD(4, "gift-card");

  private static final Map<Integer, ProductType> CODE_MAP = codeMap(ProductType.class);

  private final int code;

  private final String stringValue;

  ProductType(int code, String stringValue) {
    this.code = code;
    this.stringValue = stringValue;
  }

  @Override
  public int code() {
    return code;
  }

  @Override
  public String stringValue() {
    return stringValue;
  }

  public static ProductType fromCode(int code) {
    return getOrDefault(CODE_MAP, code, UNDEFINED);
  }

}
