package com.projectdevm8.stockalarm.service.implementation;

import com.projectdevm8.stockalarm.DTobjects.AlarmDto;

import java.util.List;

public interface AlarmService {

  List<AlarmDto> getAllForUser();

  void insert(AlarmDto alarmDto);

  void update(Long id, AlarmDto alarmDto);

  void delete(Long id);

  AlarmDto findById(Long id);
}
