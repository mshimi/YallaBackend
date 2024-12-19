package org.example.yalla_api.common.services.core;

import org.example.yalla_api.common.entities.core.Country;
import org.example.yalla_api.common.services.CrudService;
import org.example.yalla_api.common.services.PaginationService;

public interface ICountryService extends PaginationService<Country>, CrudService<Country,Long> {




}
