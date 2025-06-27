package com.pcwk.ehr.tour;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.pcwk.ehr.cmn.SearchDTO;
import com.pcwk.ehr.mapper.TourMapper;
import com.pcwk.ehr.region.domain.RegionDTO;
import com.pcwk.ehr.tour.domain.TourDTO;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml"
		,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"	})
class TourDaoTest {
	Logger log = LogManager.getLogger(getClass());
	
	TourDTO dto01;
	TourDTO dto02;
	TourDTO dto03;
	
	@Autowired
	ApplicationContext context;
	
	RegionDTO region;
	
	@Autowired
	TourMapper mapper;
	
	SearchDTO search;

	@BeforeEach
	void setUp() throws Exception {
        log.debug("context: {}", context);

        dto01 = new TourDTO(0, "관광지1", "소제목1", "상세내용1", 0,
                "서울특별시 서대문구 123", "토요일", "09:00-16:00", "010-1111-2222", 100000, 0, null);
        dto02 = new TourDTO(0, "관광지2", "소제목2", "상세내용2", 0,
                "세종특별자치시 123", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null);
        dto03 = new TourDTO(0, "관광지3", "소제목3", "상세내용3", 0,
                "세종특별자치시 풍무로", "토요일", "09:00-16:00", "010-1111-2223", 200000, 0, null);
        
        region = new RegionDTO();
        search = new SearchDTO();
    }

    @AfterEach
    void tearDown() throws Exception {
        log.debug("***************************");
        log.debug("@After");
        log.debug("***************************");
    }
    //@Disabled
    @Test
    void doRetrive()throws SQLException {
    	//1. 전체 삭제
    	mapper.deleteAll();
    	
    	//2.
    	int count = mapper.saveAll();
    	log.debug("count: "+count);
    	assertEquals(509,count);
    	
    	//paging
    	search.setPageSize(10);
    	search.setPageNo(1);
    	search.setSearchDiv("");
		search.setSearchWord("");
    	

    	region.setRegionSido("서울특별시");
    	region.setRegionGugun("서대문구");
    	
    	// Map에 담아서 전달
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("search", search);
        paramMap.put("region", region);
    	
    	List<TourDTO> list = mapper.doRetrieve(paramMap);
    	for (TourDTO vo : list) {
    	    log.debug(vo);
    	}
    }
    
    //@Disabled
    @Test
    void doUpdate() throws SQLException {
    	mapper.deleteAll();

    	// 2. regionNo 조회
        Integer regionNo = mapper.getRegionNo("서울특별시", "서대문구");
        assertNotNull(regionNo);

        // 3. 객체 전체
        region.setRegionNo(regionNo);  
        dto01.setRegion(region);  

        int flag = mapper.doSave(dto01);
        assertEquals(1, flag);


        log.debug("등록된 tourNo: {}", dto01.getTourNo());

        // 2. 조회
        TourDTO searchDTO = new TourDTO();
        searchDTO.setTourNo(dto01.getTourNo());
        TourDTO outVO = mapper.doSelectOne(searchDTO);
        assertNotNull(outVO);

        // 3. 수정
        String upString = "_U";
        int upInt = 999;

        outVO.setName(outVO.getName() + upString);
        outVO.setSubtitle(outVO.getSubtitle() + upString);
        outVO.setTel("9999-9999");

        flag = mapper.doUpdate(outVO);
        assertEquals(1, flag);

        // 4. 검증
        TourDTO updatedVO = mapper.doSelectOne(searchDTO);
        assertNotNull(updatedVO);

        assertEquals(outVO.getName(), updatedVO.getName());
        assertEquals(outVO.getSubtitle(), updatedVO.getSubtitle());
        assertEquals(outVO.getTel(), updatedVO.getTel());
    }
    @Test
    void viewsUpdate() throws SQLException {
    	mapper.deleteAll();

    	// 2. regionNo 조회
        Integer regionNo = mapper.getRegionNo("서울특별시", "서대문구");
        assertNotNull(regionNo);   

        // 객체 전체
        region.setRegionNo(regionNo);  
        dto01.setRegion(region);

        int flag = mapper.doSave(dto01);
        assertEquals(1, flag);
        
        flag = mapper.viewsUpdate(dto01);
        assertEquals(1, flag);
        
        TourDTO outDTO = mapper.doSelectOne(dto01);
        assertNotNull(outDTO);     
        
    }
    
    //@Disabled
    @Test
    void testDoSave() throws SQLException {
    	// 1. 전체 삭제
        mapper.deleteAll();

        // 2. regionNo 조회
        Integer regionNo = mapper.getRegionNo("서울특별시", "서대문구");
        assertNotNull(regionNo);

        region.setRegionNo(regionNo);  
        dto01.setRegion(region);

        int flag = mapper.doSave(dto01);
        assertEquals(1, flag);
        log.debug("저장된 tourNo: {}", dto01.getTourNo());

        // 4. 조회
        TourDTO param = new TourDTO();
        param.setTourNo(dto01.getTourNo());
        TourDTO outDTO = mapper.doSelectOne(param);

        assertNotNull(outDTO);
        assertEquals(dto01.getName(), outDTO.getName());
        assertEquals(dto01.getAddress(), outDTO.getAddress());
    }
    //@Disabled
    @Test
    void beans() {
        assertNotNull(context);
        assertNotNull(mapper);
        log.debug("context: {}", context);
        log.debug("dao: {}", mapper);
    }
}