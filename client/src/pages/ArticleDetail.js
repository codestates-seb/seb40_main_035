/* eslint-disable no-unused-vars */
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Backbutton from '../components/BackButton';
import MiniButton from '../components/MiniButton';
import SwitchToggle from '../components/SwitchToggle';
import SkillStackView from '../components/SkillStackView';
import avatar from '../assets/image/userAvatar.png';
import { BsArrowUpCircleFill, BsHeartFill } from 'react-icons/bs';
import { FiShare } from 'react-icons/fi';
import { Link, useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import {
  currentUserState,
  inputBodyState,
  interestViewState,
  skillStackViewState,
} from '../atom/atom';
import { useRecoilState, useSetRecoilState, useRecoilValue } from 'recoil';
import Comment from '../components/Comment';
import InterestView from '../components/InterestView';
import getSkills from '../utils/getSkills';
import ContentViewer from '../components/ContentViewer';
import { notiError } from '../assets/toast';

const WholeContainer = styled.div`
  background-color: var(--purple-light);
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  color: var(--black);
  padding: 0 0 60px 0;
  h3 {
    margin-right: 20px;
  }
`;

const TopContainer = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin-top: 30px;
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

    .toggle {
      height: auto;
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
    width: 100%;
    color: var(--grey-dark);
    font-size: 13px;
    font-weight: 500;
    padding: 10px;
    border-bottom: 1px solid var(--purple-medium);

    > span {
      align-items: center;
      margin-right: 10px;
    }
    a {
      color: var(--purple);
      text-decoration: none;
      margin-right: 10px;
    }
  }
  .conetent-heart-icons {
    font-size: 10px;
    margin-right: 4px;
  }
`;
const LeftRightWholeConatiner = styled.div`
  display: flex;
  width: 100%;

  @media screen and (max-width: 1200px) {
    flex-direction: column;
  }
`;
const LeftViewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 43%;
  min-width: 350px;
  background-color: white;
  border-radius: 8px;
  padding: 15px;
  margin: 30px 5px 15px 0;

  @media screen and (max-width: 1200px) {
    width: 100%;
  }
`;
const LeftViewTopBox = styled.div`
  border: 1px solid var(--purple-medium);
  display: flex;
  flex-direction: column;
  margin-bottom: 25px;
  border-radius: 8px;
  font-size: 15px;
  font-weight: 500;
  padding: 15px;
`;
const LeftViewBottomBox = styled.div`
  border: 1px solid var(--purple-medium);
  flex-direction: column;
  border-radius: 8px;
  padding: 25px;
  > ul {
    font-weight: 500;
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    font-size: 15px;
  }
  > ul > div {
    display: flex;
  }
  .project-plan-title {
    margin-right: 10px;
  }
  li {
    display: flex;
    align-items: center;
    font-size: 13px;
    margin: 10px 10px 15px 0;
    padding: 9px;
    width: auto;
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
  }
  .left-bottom-content {
    font-weight: 400;
  }
`;
const RightViewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 57%;
  min-width: 450px;
  background-color: white;
  border-radius: 8px;
  padding: 30px;
  margin: 30px 0 15px 15px;
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

  @media screen and (max-width: 1200px) {
    width: 100%;
    margin: 0 0 15px 0;
    min-height: 300px;
  }
`;
const BottomCommentConatiner = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 80%;
  min-width: fit-content;
  height: auto;
  background-color: white;
  border-radius: 8px;
  padding: 45px;
  white-space: nowrap;
  .user-info {
    font-size: 14px;
  }
  .comment {
    font-size: 13px;
  }
  input {
    width: 90%;
    height: 45px;
  }

  input,
  button {
    border: none;
    background-color: transparent;
  }

  .comment-count {
    width: 75%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
    font-weight: 500;
    color: var(--black);
  }
`;
const CommentWriteBox = styled.form`
  width: 75%;
  height: 70px;
  background-color: var(--purple-light);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin-top: 10px;
  border-radius: 8px;
  &:hover,
  &:focus,
  &:active {
    border: 2px solid var(--purple-medium);
  }

  .upload-btn {
    color: var(--purple-medium);
    font-size: 30px;
  }
`;

const ArticleDetail = () => {
  const [isCheck, setIsCheck] = useState(true);
  const [articles, setArticles] = useState([]);
  const [answers, setAnswers] = useState([]);
  const setInterestView = useSetRecoilState(interestViewState);
  const [newComment, setNewComment] = useState(false);
  const [skillStackView, setSkillStackView] =
    useRecoilState(skillStackViewState);
  const [liked, setLiked] = useState(null);
  const [answerInput, setAnswerInput] = useState('');
  const navigate = useNavigate();
  let { id } = useParams();
  const createdAtArticle = new Date(articles.createdAt);
  const currentUser = useRecoilValue(currentUserState);
  const [inputBody, setInputBody] = useRecoilState(inputBodyState);

  // 게시글 조회 http 요청
  useEffect(() => {
    axios.get(`/articles/${id}`).then((response) => {
      // 게시글 viewer 상태 set
      setInputBody(response.data.data.body);
      // articles 전체 데이터 상태
      setArticles(response.data.data);
      // 좋아요 상태 set
      setLiked(response.data.data.heartCount);
      // 관심분야 상태 set
      setInterestView(response.data.data.interests);
      // 기술스택 상태 set
      setSkillStackView(getSkills(response.data.data.skills));
      // 댓글 상태 set
      setAnswers(response.data.data.answers);
      // 모집 여부 상태 set
      setIsCheck(response.data.data.isCompleted);
    });
  }, [newComment]);

  // 게시글 삭제 이벤트 핸들러
  const onDeleteArticle = (e) => {
    e.preventDefault();
    deleteArticleSubmit();
    navigate('/');
  };
  const deleteArticleSubmit = () => {
    axios.delete(`/articles/${id}`, {
      headers: {
        // 로그인 토큰 자리
        Authorization: '',
      },
    });
  };
  const onToggleChange = () => {
    setIsCheck((isCheck) => !isCheck);
  };
  // 모집중 토글 이벤트 핸들러
  const onToggle = () => {
    if (currentUser.memberId === articles.memberId) {
      axios
        .patch(
          `/articles/${id}`,
          {
            isCompleted: !isCheck,
          },
          {
            headers: {
              // 로그인 토큰 자리
              Authorization:
                'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi7YyM656R7J20IiwibWVtYmVySWQiOjE1LCJzdWIiOiJibHVlQGdtYWlsLmNvbSIsImlhdCI6MTY2OTgxNDAwNCwiZXhwIjoxNjY5ODE1ODAzfQ.cdcc7CxX2SMnYIbipvj0_HrZKzTVwrBMITIFZP0g39bk9erlcaEkrRZ7ihkHhECh',
            },
          },
        )
        .then((response) => {
          onToggleChange(isCheck);
        });
    } else {
      notiError('수정 권한이 없습니다.');
    }
  };

  // 좋아요 이벤트 핸들러
  // const onHeartHandler = (memeberId) => {};
  // const onHeartSubmit = () => {
  //   axios
  //     .patch(
  //       `/articles/${id}`,
  //       {
  //         hearts: [
  //           {
  //             // 로그인 토큰에 따른 추후 수정
  //             // memberId: ,
  //           },
  //         ],
  //       },
  //       {
  //         headers: {
  //           // 로그인 토큰 자리
  //           Authorization: '',
  //         },
  //       },
  //     )
  //     .then((response) => {
  //       setLiked(response.data.isLiked);
  //     });
  // };

  // 댓글 등록 이벤트 핸들러
  const onAnswerHandler = (e, articleId) => {
    e.preventDefault();
    let answerValue = e.target.value;
    setAnswerInput(answerValue);
    answerSubmit(articleId);
  };
  // 댓글 Enter submit 이벤트 함수
  const onKeyPress = (e) => {
    if (e.key === 'Enter') {
      onAnswerHandler(e, articles.articleId);
    }
  };

  const onChangeAnswer = (e) => {
    setAnswerInput(e.target.value);
  };
  const answerSubmit = (articleId) => {
    axios
      .post(
        `/answers`,
        {
          articleId,
          memberId: currentUser.memberId,
          body: answerInput,
        },
        {
          headers: {
            // 로그인 토큰 자리
            Authorization: '',
          },
        },
      )
      .then((response) => {
        setNewComment(!newComment);
        setAnswerInput('');
      })
      .catch(console.error);
  };
  // 댓글 삭제 이벤트 핸들러
  const onDeleteComment = (e) => {
    e.preventDefault();
    deleteCommentSubmit(e.target.value);
  };

  const deleteCommentSubmit = (answerId) => {
    axios
      .delete(`/answers/${answerId}`, {
        headers: {
          // 로그인 토큰 자리
          Authorization: '',
        },
      })
      .then((response) => {
        setNewComment(!newComment);
      });
  };

  return (
    <WholeContainer>
      {/* 게시글 헤더 */}
      <TopContainer className="abc">
        <div className="title-box">
          <div className="title-left-box">
            <Backbutton />
            <span>{articles.title} </span>
            <div className="toggle">
              <SwitchToggle
                right="모집 완료"
                setChecked={isCheck}
                onClick={onToggle}
              />
            </div>
          </div>
          <form className="title-right-box">
            {/* 공유 버튼 */}
            <button className="title-btn">
              <span className="title-icons title-share-icons">
                <FiShare />
              </span>
            </button>
            {/* 좋아요 버튼 */}
            <button className="title-btn" onClick={() => {}}>
              <span className="title-icons title-heart-icons">
                <BsHeartFill />
              </span>
            </button>
            {/* 게시글 수정 버튼 */}

            {currentUser.memberId === articles.memberId ? (
              <>
                <MiniButton
                  onClick={() => {
                    navigate(`/articles/edit/${id}`);
                  }}
                  text={'수정하기'}
                />
                {/* 게시글 삭제 버튼 */}
                <MiniButton text={'삭제하기'} onClick={onDeleteArticle} />
              </>
            ) : (
              ''
            )}
          </form>
        </div>
        {/* 게시글 상세 정보 */}
        <div className="content-detail">
          {/* 작성자 닉네임 */}
          {currentUser.memberId === articles.memberId ? (
            <Link to={`/mypage/${currentUser.memberId}`}>
              <span>{articles.memberName}</span>
            </Link>
          ) : (
            <Link to={`/profile/${articles.memberId}`}>
              <span>{articles.memberName}</span>
            </Link>
          )}
          {/* 조회수 */}
          <span>{articles.views} view</span>
          {/* 좋아요 수 */}
          <span>
            <BsHeartFill className="conetent-heart-icons" />
            {articles.heartCount}
          </span>
          {/* 작성 일 */}
          <span>작성일 {createdAtArticle.toLocaleString()}</span>
        </div>
      </TopContainer>
      <LeftRightWholeConatiner className="abcde">
        <LeftViewContainer>
          <LeftViewTopBox>
            <span>이런 기술 스택을 사용하고 싶어요.</span>
            <SkillStackView size="12px" />
            <InterestView size="15px"></InterestView>
          </LeftViewTopBox>
          <LeftViewBottomBox>
            <ul>
              저의 숙련도는 아래와 같아요.
              <li>
                <span className="left-bottom-content">
                  {articles.memberLevel}
                </span>
              </li>
            </ul>
            <ul>
              프로젝트 예정기간
              <div>
                <li>
                  <span className="project-plan-title">시작 예정 일</span>
                  <span className="left-bottom-content">
                    {articles.startDay}
                  </span>
                </li>
                <li>
                  <span className="project-plan-title">마감 예정 일</span>
                  <span className="left-bottom-content">{articles.endDay}</span>
                </li>
              </div>
            </ul>
            <ul>
              파트별 인원 수
              <div>
                <li>
                  <span className="project-plan-title">프론트엔드 </span>
                  <span className="left-bottom-content">
                    {articles.frontend}
                  </span>
                </li>
                <li>
                  <span className="project-plan-title">백엔드 </span>
                  <span className="left-bottom-content">
                    {articles.backend}
                  </span>
                </li>
              </div>
            </ul>
          </LeftViewBottomBox>
        </LeftViewContainer>
        {/* 게시글 Viewer */}
        <ContentViewer content={inputBody} />
      </LeftRightWholeConatiner>
      {/* 댓글 컴포넌트 */}
      <BottomCommentConatiner>
        <div className="comment-count">
          <span>{articles.answerCount}개의 댓글이 있습니다.</span>
        </div>
        <Comment
          articles={articles}
          answers={answers}
          onDeleteComment={onDeleteComment}
          avatar={avatar}
          onAnswerHandler={onAnswerHandler}
          onChangeAnswer={onChangeAnswer}
          answerInput={answerInput}
        />
        <CommentWriteBox onKeyPress={onKeyPress}>
          <input
            type="text"
            className="comment-input"
            placeholder="댓글을 입력하세요."
            value={answerInput || ''} // 렌더 전 초기값이 undefined가 되지 않도록 빈문자열로 조건을 주었습니다.
            onChange={onChangeAnswer}
          ></input>
          <button type="submit">
            <BsArrowUpCircleFill
              className="upload-btn"
              onClick={(e) => {
                onAnswerHandler(e, articles.articleId);
              }}
            />
          </button>
        </CommentWriteBox>
      </BottomCommentConatiner>
    </WholeContainer>
  );
};

export default ArticleDetail;
