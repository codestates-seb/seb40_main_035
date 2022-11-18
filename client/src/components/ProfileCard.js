import styled from 'styled-components';
import avatar from '../assets/image/userAvatar.png';
import MiniButton from './MiniButton';
import { useRecoilValue } from 'recoil';
import { userProfileState, currentUserState } from '../atom/atom';

const ProfileCardWrapper = styled.div`
  width: 762px;
  height: 267px;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  border-radius: 8px;
  background-color: #fff;
  color: var(--grey-dark);
  font-size: 15px;

  .user-avatar {
    margin-right: 60px;
    overflow: visible;

    img {
      width: 152px;
      height: 152px;
      object-fit: cover;
    }
  }

  .user-info {
    max-width: calc(762px - 152px - 60px);
    .user-name {
      margin-bottom: 9px;
      font-size: 25px;
      font-weight: 900;

      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 1;
      overflow: hidden;
    }

    .user-description {
      margin-bottom: 25px;
      font-size: 15px;
      font-weight: 700;

      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 2;
      overflow: hidden;
    }

    .user-detail {
      span:first-child {
        margin-right: 63px;
        font-weight: 700;
      }
      span:last-child {
        color: var(--grey-light);
      }

      @media (max-width: 550px) {
        display: flex;
        flex-direction: column;
      }
    }

    .user-detail:not(:last-child) {
      margin-bottom: 10px;
    }
  }

  .util-btn {
    position: absolute;
    top: 20px;
    right: 20px;

    button:not(:last-child) {
      margin-right: 10px;
    }
  }
`;

const ProfileCard = ({ onEditProfile, onDeleteProfile }) => {
  const profileData = useRecoilValue(userProfileState);
  const currentUser = useRecoilValue(currentUserState);

  return (
    <ProfileCardWrapper>
      <div className="user-avatar">
        <img src={avatar} alt={`${profileData.name}'의 프로필 이미지`} />
      </div>
      <div className="user-info">
        <h3 className="user-name">{profileData.name}</h3>
        <p className="user-description">{profileData.description}</p>
        <div className="user-detail">
          <span>숙련도</span>
          <span>{profileData.level}</span>
        </div>
        <div className="user-detail">
          <span>깃허브</span>
          <span>{profileData.github}</span>
        </div>
      </div>
      {profileData.memberId === currentUser.memberId && (
        <div className="util-btn">
          <MiniButton text="수정하기" onClick={onEditProfile} />
          <MiniButton text="탈퇴하기" onClick={onDeleteProfile} />
        </div>
      )}
    </ProfileCardWrapper>
  );
};

// 이미지 출처
// <a href="https://kr.freepik.com/free-vector/people-avatars-round-icons-with-faces-of-male-and-female-characters-young-men-or-women-with-black-hair-color-different-portraits-for-social-media-and-web-design-isolated-line-art-flat-vector-set_26128227.htm">작가 upklyak</a> 출처 Freepik

export default ProfileCard;
