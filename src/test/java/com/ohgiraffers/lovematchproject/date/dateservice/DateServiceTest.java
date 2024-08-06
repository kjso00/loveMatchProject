//package com.ohgiraffers.lovematchproject.date.dateservice;
//
//import com.ohgiraffers.lovematchproject.date.datemodel.datedto.DateDTO;
//import com.ohgiraffers.lovematchproject.date.datemodel.dateentity.DateEntity;
//import com.ohgiraffers.lovematchproject.date.daterepository.DateRepository;
//import com.ohgiraffers.lovematchproject.date.dateservice.DateService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class DateServiceTest {
//
//    @Mock
//    private DateRepository dateRepository;
//
//    @InjectMocks
//    private DateService dateService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void getAllDates_shouldReturnListOfDateDTOs() {
//        // Given
//        List<DateEntity> dateEntities = Arrays.asList(
//                new DateEntity(/* 필요한 파라미터 */),
//                new DateEntity(/* 필요한 파라미터 */)
//        );
//        when(dateRepository.findAll()).thenReturn(dateEntities);
//
//        // When
//        List<DateDTO> result = dateService.getAllDates();
//
//        // Then
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        verify(dateRepository).findAll();
//    }
//
//    @Test
//    void getDateById_shouldReturnDateDTO_whenDateExists() {
//        // Given
//        Long dateId = 1L;
//        DateEntity dateEntity = new DateEntity(/* 필요한 파라미터 */);
//        when(dateRepository.findById(dateId)).thenReturn(Optional.of(dateEntity));
//
//        // When
//        DateDTO result = dateService.getDateById(dateId);
//
//        // Then
//        assertNotNull(result);
//        verify(dateRepository).findById(dateId);
//    }
//
//    @Test
//    void getDateById_shouldReturnNull_whenDateDoesNotExist() {
//        // Given
//        Long dateId = 1L;
//        when(dateRepository.findById(dateId)).thenReturn(Optional.empty());
//
//        // When
//        DateDTO result = dateService.getDateById(dateId);
//
//        // Then
//        assertNull(result);
//        verify(dateRepository).findById(dateId);
//    }
//
//    // 추가 테스트 메서드들...
//}