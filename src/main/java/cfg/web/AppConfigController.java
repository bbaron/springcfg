package cfg.web;

import cfg.model.AppConfig;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/appconfigs")
@Controller
@RooWebScaffold(path = "appconfigs", formBackingObject = AppConfig.class)
public class AppConfigController {
}
