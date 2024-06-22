package vishnumm.cloude.UserManagementAPI.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vishnumm.cloude.UserManagementAPI.Entity.ProfilePicture;
import vishnumm.cloude.UserManagementAPI.Entity.User;
import vishnumm.cloude.UserManagementAPI.Repository.ProfilePictureRepository;
import vishnumm.cloude.UserManagementAPI.Repository.UserRepository;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfilePictureServiceImpl implements ProfilePictureService {
    private final ProfilePictureRepository repository;
    private final UserRepository userRepository;

    @Override
    public ProfilePicture uploadImage( MultipartFile file, Long userId )  throws IOException {
        ProfilePicture profilePicture = new ProfilePicture();
        User user = userRepository.findById( userId )
                .orElseThrow(() -> new UsernameNotFoundException("User with this username is not fount"));
        profilePicture.setName( file.getOriginalFilename()  );
        profilePicture.setType( file.getContentType()  );
        profilePicture.setImageData( compressImage( file.getBytes()  )  );
        profilePicture.setUser( user );
        return repository.save( profilePicture );
    }

    @Override
    public ProfilePicture downloadImage( Long userId )  throws IOException {
        User user = userRepository.findById( userId ) 
                .orElseThrow( ()  -> new UsernameNotFoundException( "User with this ID is not exist" )  ) ;
        ProfilePicture profilePicture = null;
        Optional<ProfilePicture> profilePictureOpt = repository.findByUser( user ) ;
        if ( profilePictureOpt.isPresent()  )  {
            profilePicture = profilePictureOpt.get() ;
            profilePicture.setImageData( decompressImage( profilePicture.getImageData()  )  ) ;
        }
        return profilePicture;
    }

    private static byte[] compressImage( byte[] imageData )  throws IOException {
        BufferedImage image = ImageIO.read( new ByteArrayInputStream( imageData )  ) ;
        ImageWriter writer = ImageIO.getImageWritersByFormatName( "png" ) .next() ;
        ImageWriteParam param = writer.getDefaultWriteParam() ;
        param.setCompressionMode( ImageWriteParam.MODE_EXPLICIT ) ;
        param.setCompressionQuality( 0.7f ) ;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream() ;
        writer.setOutput( new MemoryCacheImageOutputStream( outputStream )  ) ;
        writer.write( null, new IIOImage( image, null, null ) , param ) ;
        return outputStream.toByteArray() ;
    }

    private static byte[] decompressImage( byte[] compressedImageData )  throws IOException {
        BufferedImage image = ImageIO.read( new ByteArrayInputStream( compressedImageData )  ) ;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream() ;
        ImageIO.write( image, "png", outputStream ) ;
        return outputStream.toByteArray() ;
    }
}
