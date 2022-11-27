// 모집중 토글 버튼 작동
// 좋아요 버튼 작동
// 9. 프로필 클릭시 유저의 프로필상세보기 페이지로 이동

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
import InterestView from '../components/InterestView';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { interestViewState, skillStackViewState } from '../atom/atom';
import { useRecoilState, useSetRecoilState } from 'recoil';
import Comment from '../components/Comment';

const WholeContainer = styled.div`
  background-color: var(--purple-light);
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  color: var(--black);
  padding: 0 45px 60px 45px;
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
  .conetent-heart-icons {
    font-size: 11px;
  }
`;
const LeftRightWholeConatiner = styled.div`
  display: flex;
  width: 80%;
`;
const LeftViewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 43%;
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
  width: 80%;
  height: auto;
  background-color: white;
  border-radius: 8px;
  padding: 45px;
  .user-info {
    font-size: 14px;
  }
  .comment {
    font-size: 13px;
  }
  input {
    width: 95%;
    height: 50px;
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
    margin-bottom: 15px;
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
  const [articles, setArticles] = useState([]);
  const [answers, setAnswers] = useState([]);
  const setInterestView = useSetRecoilState(interestViewState);
  const [newComment, setNewComment] = useState(false);
  // const [skillStackView, setSkillStackView] =
  //   useRecoilState(skillStackViewState);
  const [liked, setLiked] = useState(null);
  const [answerInput, setAnswerInput] = useState('');
  const navigate = useNavigate();
  let { id } = useParams();
  const createdAtArticle = new Date(articles.createdAt);

  // 모집중 토글 이벤트 핸들러
  const onToggle = () => {
    axios
      .patch(
        `/articles/${id}`,
        {
          isCompleted: !isCheck,
        },
        {
          headers: {
            // 로그인 기능 완료시 수정 예정
            Authorization:
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi6rmA7L2U65SpIiwibWVtYmVySWQiOjE0LCJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY2OTU0MjQxNCwiZXhwIjoxNjY5NTU2ODE0fQ.ud6q_LaGz0VWmcq_rkkqPLI2wy4z-nAdbyMDPOdczE8JTPPdwFMNHbAF4XIo5_wj',
          },
        },
      )
      .then((response) => {
        setIsCheck(response.data.isCompleted);
      });
  };

  // 좋아요 이벤트 핸들러
  // const onHeart = () => {
  //   axios
  //     .patch(
  //       `/articles/${id}`,
  //       {
  //         hearts: [
  //           {
  //             memberId: 14,
  //           },
  //         ],
  //       },
  //       {
  //         headers: {
  //           // 로그인 기능 완료시 수정 예정
  //           Authorization:
  //             'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi6rmA7L2U65SpIiwibWVtYmVySWQiOjE0LCJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY2OTU0MjQxNCwiZXhwIjoxNjY5NTU2ODE0fQ.ud6q_LaGz0VWmcq_rkkqPLI2wy4z-nAdbyMDPOdczE8JTPPdwFMNHbAF4XIo5_wj',
  //         },
  //       },
  //     )
  //     .then((response) => {
  //       setLiked(response.data.isLiked);
  //     });
  // };

  // 게시글 삭제 이벤트 핸들러
  const onDeleteArticle = (e) => {
    e.preventDefault();
    deleteArticleSubmit();
    navigate('/');
  };
  const deleteArticleSubmit = () => {
    axios.delete(`/articles/${id}`, {
      headers: {
        // 로그인 기능 완료시 수정 예정
        Authorization: '',
      },
    });
  };

  const onChangeAnswer = (e) => {
    setAnswerInput(e.currentTarget.value);
  };

  // 댓글 등록 이벤트 핸들러
  const onAnswerHandler = (e, articleId) => {
    e.preventDefault();
    let answerValue = e.currentTarget.value;
    setAnswerInput(answerValue);
    answerSubmit(articleId);
  };
  const answerSubmit = (articleId) => {
    axios
      .post(
        `/answers`,
        {
          articleId,
          memberId: 14,
          body: answerInput,
        },
        {
          headers: {
            // 로그인 기능 완료시 수정 예정
            Authorization:
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi6rmA7L2U65SpIiwibWVtYmVySWQiOjE0LCJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY2OTUzMDQ2NiwiZXhwIjoxNjY5NTQ0ODY2fQ.nKYUcLrM7g-DkEUa7PVtuoSEXncD5_H74E09QtrPEKFYmDisiHDQK0HpLu6OK_-4',
          },
        },
      )
      .then((response) => {
        setNewComment(!newComment);
        console.log(response.data.answer);
      })
      .catch(console.error);
  };
  // 댓글 수정 이벤트 핸들러
  const onUpdateComment = (e) => {
    e.preventDefault();
    let answerValue = e.currentTarget.value;
    setAnswerInput(answerValue);
    updateCommentSubmit();
  };
  const updateCommentSubmit = (answerId) => {
    axios.patch(
      `/answers/${answerId}`,
      {
        answerId,
        body: '답변2',
      },
      {
        headers: {
          // 로그인 기능 완료시 수정 예정
          Authorization:
            'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi6rmA7L2U65SpIiwibWVtYmVySWQiOjE0LCJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY2OTUzMDQ2NiwiZXhwIjoxNjY5NTQ0ODY2fQ.nKYUcLrM7g-DkEUa7PVtuoSEXncD5_H74E09QtrPEKFYmDisiHDQK0HpLu6OK_-4',
        },
      },
    );
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
          // 로그인 기능 완료시 수정 예정
          Authorization:
            'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi6rmA7L2U65SpIiwibWVtYmVySWQiOjE0LCJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY2OTUzMDQ2NiwiZXhwIjoxNjY5NTQ0ODY2fQ.nKYUcLrM7g-DkEUa7PVtuoSEXncD5_H74E09QtrPEKFYmDisiHDQK0HpLu6OK_-4',
        },
      })
      .then((response) => {
        setNewComment(!newComment);
        console.log(response);
      });
  };

  // 비동기 통신
  useEffect(() => {
    axios.get(`/articles/${id}`).then((response) => {
      setArticles(response.data.data);
      setLiked(response.data.heartCount);
      setInterestView(response.data.data.interests);
      setAnswers(response.data.data.answers);
      // setIsCheck(response.data.data.isCompleted);
      console.log(response.data.data);
    });
  }, [newComment]);

  // useEffect(() => {
  //   axios.get(`/articles/${id}`).then((response) => {
  //     setAnswers(response.data.data.answers);
  //     console.log(response.data.data);
  //   });
  // }, [newComment]);

  return (
    <WholeContainer>
      <TopContainer>
        <div className="title-box">
          <div className="title-left-box">
            <Backbutton />
            {/* 게시글 제목 */}
            <span>{articles.title} </span>
            <SwitchToggle
              right="모집 완료"
              setChecked={isCheck}
              // onClick={onToggle}
              onClick={() => {
                setIsCheck(!isCheck);
              }}
            />
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
            <MiniButton
              onClick={() => {
                navigate('/article/edit/:id');
              }}
              text={'수정하기'}
            />
            <MiniButton text={'삭제하기'} onClick={onDeleteArticle} />
          </form>
        </div>
        {/* 타이틀 하단 info */}
        <div className="content-detail">
          {/* 조회수 */}
          <span>{articles.views} view</span>
          {/* 좋아요 수 */}
          <span>
            <BsHeartFill className="conetent-heart-icons" />{' '}
            {articles.heartCount}
          </span>
          {/* 작성 일 */}
          <span>작성일 {createdAtArticle.toLocaleString()}</span>
        </div>
      </TopContainer>
      <LeftRightWholeConatiner>
        <LeftViewContainer>
          <LeftViewTopBox>
            <span>이런 기술 스택을 사용하고 싶어요.</span>
            <SkillStackView size="12px" />
            <InterestView content={'이런 산업군에 관심이 있어요'} size="15px" />
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
        <RightViewContainer>
          <span className="content-plan">프로젝트 계획을 설명해 주세요!</span>
          <span>{articles.body}</span>
        </RightViewContainer>
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
          onUpdateComment={onUpdateComment}
          answerInput={answerInput}
        />
        <CommentWriteBox>
          <input
            type="text"
            className="comment-input"
            placeholder="댓글을 입력하세요."
            value={answerInput}
            onChange={onChangeAnswer}
          ></input>
          <button>
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
