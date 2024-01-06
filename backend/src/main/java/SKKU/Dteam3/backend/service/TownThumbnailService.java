package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.TownThumbnail;
import SKKU.Dteam3.backend.repository.TownThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TownThumbnailService {

    private final TownThumbnailRepository townThumbnailRepository;

    @Value("${townThumbnail.file-dir}")
    private String fileDir;

    public UUID addTownThumbnail(MultipartFile thumbnailFile, Town town){
        if(thumbnailFile.isEmpty()){
            return null;
        }
        try{
            TownThumbnail townThumbnail = new TownThumbnail(town, thumbnailFile.getOriginalFilename());
            uploadThumbnail(thumbnailFile, townThumbnail.getId(), town);
            townThumbnailRepository.save(townThumbnail);
            return townThumbnailRepository.findUUIDByTownId(townThumbnail.getTown().getId());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("썸네일 사진이 올바르지 않습니다.");
        } catch (IOException e) {
            throw new RuntimeException("썸네일 업로드에 실패하였습니다.");
        }
    }

    public Resource downloadTownThumbnail(Long townId) {

        try {
            UUID uuid = townThumbnailRepository.findUUIDByTownId(townId);
            return new UrlResource("file:" + fileDir +  uuid.toString()
                    + townThumbnailRepository.findByUUID(uuid).getOriginalName());
        } catch (MalformedURLException e) {
            throw new RuntimeException("썸네일 다운로드에 실패하였습니다.");
        }
    }

    private void uploadThumbnail(MultipartFile thumbnailFile, UUID id, Town town) throws IOException {
        String storeFileName = createStoreFileName(thumbnailFile.getOriginalFilename());
        storeFileName = id.toString() + storeFileName;
        thumbnailFile.transferTo(new File(getFullPath(storeFileName)));
        TownThumbnail townThumbnail = new TownThumbnail(town, thumbnailFile.getOriginalFilename());
        townThumbnailRepository.save(townThumbnail);
    }

    private String getFullPath(String storeFileName) {
        return fileDir + storeFileName;
    }

    private String createStoreFileName(String originalFilename) {
        return extractExt(originalFilename);
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos+1);
    }
}
