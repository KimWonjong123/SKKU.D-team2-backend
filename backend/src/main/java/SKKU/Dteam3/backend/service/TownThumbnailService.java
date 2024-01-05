package SKKU.Dteam3.backend.service;

import SKKU.Dteam3.backend.domain.Town;
import SKKU.Dteam3.backend.domain.TownThumbnail;
import SKKU.Dteam3.backend.repository.TownThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TownThumbnailService {

    private final TownThumbnailRepository townThumbnailRepository;

    @Value("${townThumbnail.file-dir}")
    private String fileDir;

    public UUID addTownThumbnail(MultipartFile thumbnailFile, Town town) throws IOException {
        if(thumbnailFile.isEmpty()){
            return null;
        }
        try{
            TownThumbnail townThumbnail = new TownThumbnail(town, thumbnailFile.getOriginalFilename());
            uploadThumbnail(thumbnailFile, townThumbnail.getId());
            townThumbnailRepository.save(townThumbnail);
            return townThumbnailRepository.findByTownId(townThumbnail.getTown().getId());
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException("썸네일 사진이 올바르지 않습니다.");
        }
        catch(IOException e1){
            throw new IOException("썸네일 업로드에 실패하였습니다");
        }
    }

    private void uploadThumbnail(MultipartFile thumbnailFile, UUID id) throws IOException {
        String storeFileName = createStoreFileName(thumbnailFile.getOriginalFilename());
        storeFileName = id.toString() + storeFileName;
        thumbnailFile.transferTo(new File(getFullPath(storeFileName)));
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
