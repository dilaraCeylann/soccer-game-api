package com.dilaraceylan.soccergame.enums;
/**
 * @author dilara.ceylan
 */
public enum PositionEnum {

    GOALKEEPERS((byte) 0, "GOALKEEPERS"), DEFENDERS((byte) 1, "DEFENDERS"), MIDFIELDERS((byte) 2, "MIDFIELDERS"), ATTACKERS((byte) 3, "ATTACKERS");

	private byte value;
	private String caption;

	private PositionEnum(byte value, String caption) {
		this.value = value;
		this.caption = caption;
	}

	public byte getValue() {
		return value;
	}

	public String getCaption() {
		return caption;
	}

	public static PositionEnum getEnum(byte value) {
		for (PositionEnum item : PositionEnum.values()) {
			if (item.value == value) {
				return item;
			}
		}
		return null;
	}
}
