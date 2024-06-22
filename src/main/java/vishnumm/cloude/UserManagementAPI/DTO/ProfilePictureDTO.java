package vishnumm.cloude.UserManagementAPI.DTO;

import jakarta.persistence.Column;

public class ProfilePictureDTO {
    private Long id;
    private String name;
    private String type;
    private byte[] imageData;
    private Long userId;
}
