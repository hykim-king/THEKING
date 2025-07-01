package com.pcwk.ehr.image.service;

import java.sql.SQLException;

import com.pcwk.ehr.image.domain.ImageDTO;

public interface ImageService {

	int doDelete(ImageDTO param);

	int doUpdate(ImageDTO param);

	int doSave(ImageDTO param) throws SQLException;

}
