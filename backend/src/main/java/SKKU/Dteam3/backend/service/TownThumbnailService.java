package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.TownThumbnail;
import SKKU.Dteam3.backend.repository.TownThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TownThumbnailService {

    private final TownThumbnailRepository townThumbnailRepository;

    @Value("${townThumbnail.file-dir}")
    private String fileDir;


    @Value("${townThumbnail.file-ext}")
    private String fileExt;

    public UUID addTownThumbnail(MultipartFile thumbnailFile, Town town){
        if(thumbnailFile.isEmpty()){
            throw new RuntimeException("파일이 비어있습니다.");
        }
        try{
            TownThumbnail townThumbnail = new TownThumbnail(town, thumbnailFile.getOriginalFilename());
            townThumbnailRepository.save(townThumbnail);
            uploadThumbnail(thumbnailFile, townThumbnail.getId());
            return townThumbnailRepository.findUUIDByTownId(townThumbnail.getTown().getId());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("썸네일 사진이 올바르지 않습니다.");
        }
    }

    public Resource downloadTownThumbnail(Long townId) {
        try {
            UUID uuid = townThumbnailRepository.findUUIDByTownId(townId);
            return new UrlResource("file:" + fileDir + uuid.toString()
                    + townThumbnailRepository.findByUUID(uuid).getOriginalName());
        } catch (MalformedURLException e) {
            throw new RuntimeException("썸네일 다운로드에 실패하였습니다.");
        }
    }

    private boolean uploadThumbnail(MultipartFile thumbnailFile, UUID id) {
        try{
            createStoreFileName(thumbnailFile.getOriginalFilename());
            String storeFileName = id.toString() + thumbnailFile.getOriginalFilename();
            thumbnailFile.transferTo(new File(getFullPath(storeFileName)));
            return true;
        }catch (IOException e){
            throw new IllegalArgumentException("이미지 저장에 실패하였습니다");
        }
    }

    private String getFullPath(String storeFileName) {
        return fileDir + storeFileName;
    }

    private Boolean createStoreFileName(String originalFilename) {
        String ext = extractExt(originalFilename);
        return checkExt(ext);
    }

    private Boolean checkExt(String ext) {
        List<String> fileExtList = List.of(fileExt.split(","));
        for(String Ext : fileExtList){
            if(Ext.equalsIgnoreCase(ext)){
                return true;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 확장자입니다.");
    }

    private String extractExt(String originalFilename) {
        if(Arrays.stream(originalFilename.split("\\.")).count()!=2){
            throw new IllegalArgumentException("올바르지 않은 파일 이름입니다.");
        }
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }
}
