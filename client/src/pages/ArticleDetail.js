/* eslint-disable no-unused-vars */
import { useState } from 'react';
import styled from 'styled-components';
import Backbutton from '../components/BackButton';
import MiniButton from '../components/MiniButton';
import SwitchToggle from '../components/SwitchToggle';
import SkillStackView from '../components/SkillStackView';
import avatar from '../assets/image/userAvatar.png';
import { BsArrowUpCircleFill, BsHeartFill } from 'react-icons/bs';
// import { CiShare1 } from 'react-icons/ci';
// import { FaHeart } from 'react-icons/fa';
import { FiShare } from 'react-icons/fi';
import InterestView from '../components/InterestView';
import { useNavigate } from 'react-router-dom';

const WholeContainer = styled.div`
  background-color: var(--purple-light);
  width: 100%;
  height: 115vh;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  color: var(--black);
  h3 {
    margin-right: 20px;
  }
`;

const TopContainer = styled.div`
  width: 80%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-top: 40px;
  align-items: center;

  .title-box {
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .title-left-box {
    margin-left: -50px;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    font-weight: 500;
    > span {
      margin-left: 10px;
      margin-right: 20px;
    }
  }
  .title-right-box {
    display: flex;
    align-items: center;
    height: 100%;

    .title-btn {
      border: none;
      background-color: transparent;
      > span {
        font-size: 20px;
        margin-right: 5px;
        color: var(--purple-medium-dark);
        border: none;
        background-color: transparent;
      }
    }
    > button {
      margin-left: 10px;
    }
  }
  .content-detail {
    /* display: flex; */
    /* align-items: center; */
    width: 100%;
    color: var(--grey-dark);
    font-size: 13px;
    font-weight: 500;
    padding: 10px;
    border-bottom: 1px solid var(--purple-medium);
    margin-bottom: 10px;
    /* border-bottom: 1px solid var(--pruple-medium); */
    > span {
      align-items: center;
      margin-right: 10px;
    }
  }
`;
const LeftRightWholeConatiner = styled.div`
  display: flex;
  width: 80%;
`;
const LeftViewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 45%;
  min-width: 350px;
  background-color: white;
  border-radius: 8px;
  padding: 30px;
  margin: 25px 15px 25px 0;
`;
const LeftViewTopBox = styled.div`
  border: 1px solid var(--purple-medium);
  display: flex;
  flex-direction: column;
  margin-bottom: 25px;
  border-radius: 9px;
  font-size: 15px;
  font-weight: 500;
  padding: 25px;
`;
const LeftViewBottomBox = styled.div`
  border: 1px solid var(--purple-medium);
  flex-direction: column;
  margin-bottom: 10px;
  border-radius: 9px;
  padding: 25px;
  > ul {
    font-weight: 500;
  }
  > ul > div {
    display: flex;
  }
  li {
    font-size: 13px;
    margin: 10px 10px 15px 0;
    padding: 9px;
    width: auto;
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
    > span {
      width: auto;
    }
  }
  .left-bottom-content {
    font-weight: 400;
  }
`;
const RightViewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 55%;
  background-color: white;
  border-radius: 8px;
  padding: 30px;
  margin: 25px 0 25px 15px;
  .content-plan {
    margin-bottom: 20px;
    padding-bottom: 20px;
    font-size: 15px;
    font-weight: 500;
    border-bottom: 1px solid var(--purple-medium);
  }
  span {
    font-size: 15px;
  }
`;
// 하단 댓글 컨테이너
const BottomCommentConatiner = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 80vw;
  height: auto;
  background-color: white;
  border-radius: 8px;
  padding: 45px;
  input {
    width: 100%;
    height: 50px;
  }
  input,
  button {
    border: none;
    background-color: transparent;
  }

  .comment-count {
    width: 80%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 25px;
    font-weight: 500;
    color: var(--black);
  }
`;
const CommentBox = styled.form`
  display: flex;
  flex-direction: column;
  width: 80%;
  justify-content: space-between;
  align-items: flex-start;
  padding-bottom: 20px;

  .user {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    .user-info {
      display: flex;
      align-items: center;
    }
    .comment-created {
      margin-left: 40px;
    }

    img {
      width: 25px;
      height: 25px;
      margin-right: 10px;
    }
  }

  .comment-btn {
    display: flex;
    button {
      padding-left: 15px;
      font-weight: 500;
      font-size: 13px;
      color: var(--grey-dark);
    }
  }

  .comment {
    padding: 10px;
    margin-left: 25px;
  }
`;
const CommnetWriteBox = styled.form`
  width: 80%;
  height: 70px;
  background-color: var(--grey-light);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 35px;
  margin-top: 20px;
  border-radius: 8px;
  button {
    /* width: 25px;
    height: 25px; */
    /* margin: 10px 25px 10px 25px; */

    .upload-btn {
      color: var(--purple-medium);
      font-size: 30px;
    }
  }
`;

const ArticleDetail = () => {
  const [isCheck, setIsCheck] = useState(true);
  const navigate = useNavigate();
  return (
    <WholeContainer>
      <TopContainer>
        <div className="title-box">
          <div className="title-left-box">
            <Backbutton />
            <span>프로젝트 제목</span>
            <SwitchToggle
              right="모집 완료"
              setChecked={isCheck}
              onClick={() => {
                setIsCheck(!isCheck);
              }}
            />
          </div>
          <form className="title-right-box">
            <button className="title-btn">
              <span className="title-icons">
                <FiShare />
              </span>
            </button>
            <button className="title-btn">
              <span className="title-icons">
                <BsHeartFill />
              </span>
            </button>
            <MiniButton
              onClick={() => {
                navigate('/article/edit/:id');
              }}
              text={'수정하기'}
            />
            <MiniButton text={'삭제하기'} />
          </form>
        </div>
        {/* 타이틀 하단 info */}
        <div className="content-detail">
          {/* 조회수 */}
          <span>321 view</span>
          {/* 좋아요 수 */}
          <span>
            <BsHeartFill /> 3
          </span>
          {/* 작성 일 */}
          <span>작성일 2022.11.12</span>
        </div>
      </TopContainer>
      <LeftRightWholeConatiner>
        <LeftViewContainer>
          <LeftViewTopBox>
            <span>이런 기술 스택을 사용하고 싶어요.</span>
            <SkillStackView />
            {/* <h4>이런 산업군에 관심이 있어요.</h4> */}
            <InterestView />
          </LeftViewTopBox>
          <LeftViewBottomBox>
            <ul>
              프로젝트 예정기간
              <div>
                <li>
                  <span>시작 예정 일</span>
                  <span className="left-bottom-content"> 2022.11.01</span>
                </li>
                <li>
                  {' '}
                  <span>마감 예정 일</span>
                  <span className="left-bottom-content"> 2022.12.01</span>
                </li>
              </div>
            </ul>
            <ul>
              파트별 인원 수
              <div>
                <li>
                  <span>프론트엔드 </span>
                  <span className="left-bottom-content">2</span>
                </li>
                <li>
                  <span>백엔드 </span>
                  <span className="left-bottom-content">3</span>
                </li>
              </div>
            </ul>
            <ul>
              이런 분과 함께 하고 싶어요.
              <li>
                <span className="left-bottom-content">학생,취준생</span>
              </li>
            </ul>
          </LeftViewBottomBox>
        </LeftViewContainer>
        <RightViewContainer>
          <span className="content-plan">프로젝트 계획을 설명해 주세요!</span>
          <span>프로젝트 내용 설명</span>
        </RightViewContainer>
      </LeftRightWholeConatiner>
      <BottomCommentConatiner>
        <div className="comment-count">
          <span>개의 댓글이 있습니다.</span>
        </div>
        <CommentBox>
          <div className="user">
            <div className="user-info">
              <img src={avatar} alt="" />
              <span className="user-name">김코딩</span>
              <span className="comment-created">22.11.1</span>
            </div>
            <span className="comment-btn">
              <button>수정하기</button>
              <button>삭제하기</button>
            </span>
          </div>

          <span className="comment">댓글 내용입니다.</span>
        </CommentBox>

        <CommnetWriteBox>
          <input
            className="comment-input"
            placeholder="댓글을 입력하세요."
          ></input>
          <button>
            <BsArrowUpCircleFill className="upload-btn" />
          </button>
        </CommnetWriteBox>
      </BottomCommentConatiner>
    </WholeContainer>
  );
};

export default ArticleDetail;
