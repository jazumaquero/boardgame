package com.zcia.boardgame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcia.boardgame.model.Item;

@Controller
public class ItemController {

	@Autowired
	private Item item;

	@RequestMapping("/item")
	public @ResponseBody Item item() {
		return item;
	}
}
