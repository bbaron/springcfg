package org.github.bbaron.springcfg.example.webapp.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.github.bbaron.springcfg.example.webapp.model.AppConfig;
import org.github.bbaron.springcfg.example.webapp.model.AppConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;


@RequestMapping("/appconfigs")
@Controller
public class AppConfigController {

	@Autowired
    AppConfigRepository appConfigRepository;

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid AppConfig appConfig, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appConfig);
            return "appconfigs/create";
        }
        uiModel.asMap().clear();
        appConfigRepository.save(appConfig);
        return "redirect:/appconfigs/" + encodeUrlPathSegment(appConfig.getId().toString(), httpServletRequest);
    }

	@RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new AppConfig());
        return "appconfigs/create";
    }

	@RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("appconfig", appConfigRepository.findOne(id));
        uiModel.addAttribute("itemId", id);
        return "appconfigs/show";
    }

	@RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("appconfigs", appConfigRepository.findAll(new org.springframework.data.domain.PageRequest(firstResult / sizeNo, sizeNo)).getContent());
            float nrOfPages = (float) appConfigRepository.count() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("appconfigs", appConfigRepository.findAll());
        }
        return "appconfigs/list";
    }

	@RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid AppConfig appConfig, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, appConfig);
            return "appconfigs/update";
        }
        uiModel.asMap().clear();
        appConfigRepository.save(appConfig);
        return "redirect:/appconfigs/" + encodeUrlPathSegment(appConfig.getId().toString(), httpServletRequest);
    }

	@RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, appConfigRepository.findOne(id));
        return "appconfigs/update";
    }

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        AppConfig appConfig = appConfigRepository.findOne(id);
        appConfigRepository.delete(appConfig);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/appconfigs";
    }

	void populateEditForm(Model uiModel, AppConfig appConfig) {
        uiModel.addAttribute("appConfig", appConfig);
    }

	String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
}
