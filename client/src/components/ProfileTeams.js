import styled from 'styled-components';
import avatar from '../assets/image/userAvatar.png';

const ProfileContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  margin-top: 20px;
  padding: 15px;
  text-align: center;
  img {
    width: 120px;
  }
  .name {
    padding: 3px;
  }
  .part {
  }
`;

const ProfileTeams = ({ name, part }) => {
  return (
    <ProfileContainer>
      <div>
        <div className="profile-img">
          <img alt="profile" src={avatar} />
        </div>
        <div className="name">{name}</div>
        <div className="part">{part}</div>
      </div>
    </ProfileContainer>
  );
};

export default ProfileTeams;
