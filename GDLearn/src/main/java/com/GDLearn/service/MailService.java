package com.GDLearn.service;

import com.GDLearn.dto.RegistryDto;
import com.GDLearn.lang.Result;

public interface MailService {

    Result sendMail(RegistryDto dto);
}
