import styled from 'styled-components';
import avatar from '../assets/image/userAvatar.png';

const ProfileCardWrapper = styled.div`
  width: 762px;
  height: 267px;
  display: flex;
  justify-content: center;
  align-items: center;
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
`;

// 회원 조회 데이터 props 전달 예시
// <ProfileCard
//  name={dummy.data.name}
//  description={dummy.data.description}
//  level={dummy.data.level}
//  github={dummy.data.github}
// />

const ProfileCard = ({ name, description, level, github }) => {
  return (
    <ProfileCardWrapper>
      <div className="user-avatar">
        <img src={avatar} alt={`${name}'의 프로필 이미지`} />
      </div>
      <div className="user-info">
        <h3 className="user-name">{name}</h3>
        <p className="user-description">{description}</p>
        <div className="user-detail">
          <span>숙련도</span>
          <span>{level}</span>
        </div>
        <div className="user-detail">
          <span>깃허브</span>
          <span>{github}</span>
        </div>
      </div>
    </ProfileCardWrapper>
  );
};

export default ProfileCard;
