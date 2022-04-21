package al.ikubinfo.hrmanagement.services;

import al.ikubinfo.hrmanagement.converters.RequestConverter;
import al.ikubinfo.hrmanagement.dto.RequestDto;
import al.ikubinfo.hrmanagement.model.RequestEntity;
import al.ikubinfo.hrmanagement.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestService {
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestConverter requestConverter;

    public List<RequestDto> getRequests(){
        return requestRepository
                .findAll()
                .stream()
                .map(requestConverter::toDto)
                .collect(Collectors.toList());
    }

    public RequestDto createRequest(RequestDto requestDto){
        RequestEntity requestEntity = requestConverter.toEntity(requestDto);
        requestRepository.save(requestEntity);
        return requestDto;
    }

    public boolean deleteRequest(Long id){
        RequestEntity requestEntity = requestRepository.getById(id);
        requestEntity.setDeleted(true);
        requestRepository.save(requestEntity);
        return true;
    }

    public RequestDto updateRequest(RequestDto requestDto){
        RequestEntity requestEntity = requestConverter.toEntity(requestDto);
        requestRepository.save(requestEntity);
        return requestDto;
    }

}
