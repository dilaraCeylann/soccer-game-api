package com.dilaraceylan.soccergame.enums;
/**
 * @author dilara.ceylan
 */
public enum RoleEnum {

    ROLE_USER((byte) 0, "ROLE_USER"), ROLE_ADMIN((byte) 1, "ROLE_ADMIN");

	private byte value;
	private String caption;

	private RoleEnum(byte value, String caption) {
		this.value = value;
		this.caption = caption;
	}

	public byte getValue() {
		return value;
	}

	public String getCaption() {
		return caption;
	}

	public static RoleEnum getEnum(byte value) {
		for (RoleEnum item : RoleEnum.values()) {
			if (item.value == value) {
				return item;
			}
		}
		return null;
	}
}
