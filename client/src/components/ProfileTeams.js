import styled from 'styled-components';
import avatar from '../assets/image/userAvatar.png';

const ProfileContainer = styled.div`
  img {
  }
  .name {
  }
  .part {
  }
`;

const ProfileTeams = ({ name, part }) => {
  return (
    <ProfileContainer>
      <div>
        <div className="about-profile-img">
          <img alt="profile" src={avatar} />
        </div>
        <div className="about-name">{name}</div>
        <div className="about-part">{part}</div>
      </div>
    </ProfileContainer>
  );
};

export default ProfileTeams;
