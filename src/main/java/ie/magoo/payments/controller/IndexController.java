package ie.magoo.payments.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The Class IndexController.
 * 
 * Render the stripe page and process any submitted payment.
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String index1(@RequestParam Map<String, String> request, Model model) {
		return "index";
	}

	@RequestMapping("/index")
	public String index2(@RequestParam Map<String, String> request, Model model) {
		return "index";
	}

}
