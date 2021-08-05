package com.projectdevm8.stockalarm.controller;

import com.projectdevm8.stockalarm.ConfigEx.exception.ServiceException;
import com.projectdevm8.stockalarm.DTobjects.AlarmDto;
import com.projectdevm8.stockalarm.service.implementation.AlarmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.projectdevm8.stockalarm.controller.ThymeLeafConstants.*;

@Controller
@RequestMapping("/alarms")
public class AlarmController {
  private static Logger logger = LogManager.getLogger(AlarmController.class);

  private final AlarmService service;

  public AlarmController(AlarmService service) {
    this.service = service;
  }

  @GetMapping()
  public String getDisplayAlarmsPage(Model model) {
    List<AlarmDto> alarms = service.getAllForUser();
    logger.info(String.format("Display Alarms: Utilizatorul are %d alarme.", alarms.size()));
    model.addAttribute(ALARMS_ATTRIBUTE, alarms);
    return ALARMS_PAGE;
  }

  @GetMapping("/add")
  public String getAddAlarmPage(Model model) {
    model.addAttribute(ALARM_ATTRIBUTE, new AlarmDto());
    return ADD_ALARM_PAGE;
  }

  @PostMapping()
  public String add(@ModelAttribute(value = "newAlarm") AlarmDto alarmDto, Model model) {
    logger.info("Received request for add alarm.");
    try {
      service.insert(alarmDto);
      return "redirect:/";
    } catch (ServiceException e) {
      logger.error("Received exception:", e);
      model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
      model.addAttribute(ALARM_ATTRIBUTE, alarmDto);
      return ADD_ALARM_PAGE;
    }
  }

  @GetMapping("/edit")
  public String getEditAlarmPage(HttpServletRequest request, Model model) {
    Long id = Long.valueOf(request.getParameter("id"));
    AlarmDto alarm = service.findById(id);
    model.addAttribute(ALARM_ATTRIBUTE, alarm);
    return EDIT_ALARM_PAGE;
  }

  @PostMapping("/edit")
  public String update(@ModelAttribute(value = "newAlarm") AlarmDto alarmDto, Model model) {
    logger.info("Received request for update alarm");
    try {
      service.update(alarmDto.getId(), alarmDto);
      return "redirect:/";
    } catch (ServiceException e) {
      logger.error("Received exception:", e);
      model.addAttribute(ERROR_ATTRIBUTE, e.getMessage());
      model.addAttribute(ALARM_ATTRIBUTE, alarmDto);
      return EDIT_ALARM_PAGE;
    }
  }

  @DeleteMapping ("/delete")
  public String delete(@ModelAttribute(value = "id") Long id) {
    logger.info("Received request for delete alarm");
    service.delete(id);
    return "redirect:/";
  }
}
