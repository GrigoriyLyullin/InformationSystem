package com.railwaycompany.business.services.interfaces;

import com.railwaycompany.business.dto.UserData;

public interface UserService {

    UserData getUserData(int userId);

    Integer getUserIdByUsername(String username);
}
