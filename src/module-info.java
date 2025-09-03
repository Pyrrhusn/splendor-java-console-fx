module splendor_g036 {
	exports persistentie;
	exports ui;
	exports util;
	exports gui;
	exports language;
	exports main;
	exports domein;
	exports guitest;
	exports dto;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;

	opens main to javafx.graphics;
}