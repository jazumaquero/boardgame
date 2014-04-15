package com.zcia.boardgame.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zcia.boardgame.model.Board;
import com.zcia.boardgame.model.BoardSize;
import com.zcia.boardgame.model.Resource;

/**
 * This class includes a set of RESTFUL services will be used to manage
 * any board used in the platform.
 * Use Cases:
 *
 * - Create a new board (backend).
 * - Update an existing board (backend).
 * - Retrieve data from some board (backend and frontend).
 *
 * @author jazumaquero
 *
 * TODO:
 * - Review use cases
 * - Analyze if it's possible to change URL resource path to 'board/something'
 */
@Controller
public class BoardController {

	private final Logger log = Logger.getLogger(BoardController.class);

	private final static String GET_BOARD = "/board_get";
	private final static String CREATE_BOARD = "/board_create";
	private final static String UPDATE_BOARD = "/board_update";

	@Autowired
	private Board board;

	private Map<Long, Board> boards;

	/**
	 * This method creates the hash map used to store all board on the platform
	 * TODO:
	 * Current method is create a empty hash map, but, in the future there two
	 * strategies to be taken into account:
	 * 1) Load data from properties (needs platform reload)
	 * 2) Use a distributed cache with persistence (preferred, but most complex)
	 */
	@PostConstruct
	private void init() {
		boards = new HashMap<Long, Board>();
	}

	/**
	 * This is a sample request only used to test the controller.
	 * @return a board that is loaded from a properties file.
	 */
	@RequestMapping("/board")
	public @ResponseBody Board board() {
		return board;
	}

	/**
	 * Implements a RESTFUL services that creates a new board if it doesn't
	 * exists yet.
	 * @param id
	 * @param name
	 * @param version
	 * @param height
	 * @param width
	 * @param bg_name
	 * @param bg_uri
	 * @return
	 */
	@RequestMapping(CREATE_BOARD)
	public @ResponseBody Board create(
			@RequestParam(value = "id", required = true) long id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "version", required = false, defaultValue = "0.0.1 beta") String version,
			@RequestParam(value = "size_height", required = false, defaultValue = "-1") int height,
			@RequestParam(value = "size_width", required = false, defaultValue = "-1") int width,
			@RequestParam(value = "background_name", required = false, defaultValue = "-1") String bg_name,
			@RequestParam(value = "background_uri", required = false, defaultValue = "-1") String bg_uri) {
		if (boards.containsKey(id)) {
			log.warn("Requested board is created yet!(id=" + id + ")");
			return null;
		}
		createNewBoard(id, name, version, height, width, bg_name, bg_uri);
		return boards.get(id);
	}

	/**
	 * Implements a RESTFUL service that retrieve a board using id.
	 * @param id
	 * @return the board if exists, or null when it doesn't.
	 */
	@RequestMapping(GET_BOARD)
	public @ResponseBody Board get(@RequestParam(value = "id", required = true) long id) {
		Board board = boards.get(id);
		if (board == null) {
			log.warn("Requested board is not found!(id=" + id + ")");
		} else {
			log.warn("Requested board (id=" + id + ")");
		}
		return board;
	}

	/**
	 * Implements a RESTFUL service to update a previous created board.
	 * @param id
	 * @param name
	 * @param version
	 * @param height
	 * @param width
	 * @param bg_name
	 * @param bg_uri
	 * @return the updated board, or null if board didn't found.
	 */
	@RequestMapping(UPDATE_BOARD)
	public @ResponseBody Board update(
			@RequestParam(value = "id", required = true) long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "size_height", required = false) int height,
			@RequestParam(value = "size_width", required = false) int width,
			@RequestParam(value = "background_name", required = false) String bg_name,
			@RequestParam(value = "background_uri", required = false) String bg_uri) {
		if (boards.containsKey(id)) {
			updateBoard(id, name, version, 0, 0, bg_name, bg_uri);
			return boards.get(id);
		} else {
			log.warn("Requested board is not found!(id=" + id + ")");
			return null;
		}
	}

	/**
	 * Creates a new board and insert into the hash map
	 * @param id
	 * @param name
	 * @param version
	 * @param height
	 * @param width
	 * @param bg_name
	 * @param bg_uri
	 */
	private void createNewBoard(long id, String name, String version,
			int height, int width, String bg_name, String bg_uri) {
		Board board = new Board();
		BoardSize size = new BoardSize();
		Resource background = new Resource();
		size.setHeight(height);
		size.setWidth(width);
		background.setName(bg_name);
		background.setUri(URI.create(bg_uri));
		board.setId(id);
		board.setName(name);
		board.setVersion(version);
		board.setSize(size);
		board.setBackground(background);
		boards.put(id, board);
		log.info("Created board (id=" + id + ")");
	}

	/**
	 * Updates a board from the hash map.
	 * @param id
	 * @param name
	 * @param version
	 * @param height
	 * @param width
	 * @param bg_name
	 * @param bg_uri
	 */
	private void updateBoard(long id, String name, String version, int height,
			int width, String bg_name, String bg_uri) {
		Board board = boards.get(id);
		if (name != null)
			board.setName(name);
		if (version != null)
			board.setVersion(version);
		if (bg_name != null)
			board.getBackground().setName(bg_name);
		if (bg_uri != null)
			board.getBackground().setUri(URI.create(bg_uri));
		boards.put(id, board);
		log.info("Board update (id=" + id + ")");
	}
}
