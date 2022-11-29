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
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import { interestViewState, skillStackViewState } from '../atom/atom';
import { useRecoilState, useSetRecoilState } from 'recoil';
import Comment from '../components/Comment';
import InterestView from '../components/InterestView';

const WholeContainer = styled.div`
  background-color: var(--purple-light);
  width: 100%;
  height: 100%;
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
    width: 100%;
    color: var(--grey-dark);
    font-size: 13px;
    font-weight: 500;
    padding: 10px;
    border-bottom: 1px solid var(--purple-medium);
    margin-bottom: 10px;
    > span {
      align-items: center;
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

  // 기술스택 상태 set 함수
  const onSkills = (skills) => {
    for (let el of skills) {
      if (el.skillSort === '프론트엔드') {
        setSkillStackView([
          {
            tabTitle: '프론트엔드',
            tabCont: [
              ...skillStackView[0].tabCont,
              {
                skillId: skillStackView[0].tabCont.length + 1,
                name: el.name,
              },
            ],
          },
          {
            tabTitle: '백엔드',
            tabCont: [...skillStackView[1].tabCont],
          },
          {
            tabTitle: '기타',
            tabCont: [...skillStackView[2].tabCont],
          },
        ]);
      }
      if (el.skillSort === '백엔드') {
        setSkillStackView([
          {
            tabTitle: '프론트엔드',
            tabCont: [...skillStackView[0].tabCont],
          },
          {
            tabTitle: '백엔드',
            tabCont: [
              ...skillStackView[1].tabCont,
              {
                skillId: skillStackView[1].tabCont.length + 1,
                name: el.name,
              },
            ],
          },
          {
            tabTitle: '기타',
            tabCont: [...skillStackView[2].tabCont],
          },
        ]);
      }
      if (el.skillSort !== '백엔드' && el.skillSort !== '프론트엔드') {
        setSkillStackView([
          {
            tabTitle: '프론트엔드',
            tabCont: [...skillStackView[0].tabCont],
          },
          {
            tabTitle: '백엔드',
            tabCont: [...skillStackView[1].tabCont],
          },
          {
            tabTitle: '기타',
            tabCont: [
              ...skillStackView[2].tabCont,
              {
                skillId: skillStackView[2].tabCont.length + 1,
                name: el.name,
              },
            ],
          },
        ]);
      }
    }
  };

  // 게시글 조회 http 요청
  useEffect(() => {
    axios.get(`/articles/${id}`).then((response) => {
      // articles 전체 데이터 상태
      setArticles(response.data.data);
      // 좋아요 상태 set
      setLiked(response.data.data.heartCount);
      // 관심분야 상태 set
      setInterestView(response.data.data.interests);
      // 기술스택 상태 set
      onSkills(response.data.data.skills);
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
              'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi6rmA7L2U65SpIiwibWVtYmVySWQiOjE0LCJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImlhdCI6MTY2OTYxNDEzNCwiZXhwIjoxNjY5NjI4NTM0fQ.lNABbLSSk6hsmiG3uYQZXekuCCPVMGY--uuGcRWCKkvFl4jkmjLn61-rj4HJN88x',
          },
        },
      )
      .then((response) => {
        onToggleChange(isCheck);
      });
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
          memberId: 14,
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
      <TopContainer>
        <div className="title-box">
          <div className="title-left-box">
            <Backbutton />
            <span>{articles.title} </span>
            <SwitchToggle
              right="모집 완료"
              setChecked={isCheck}
              onClick={onToggle}
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
            {/* 게시글 수정 버튼 */}
            <MiniButton
              onClick={() => {
                navigate('/article/edit/:id');
              }}
              text={'수정하기'}
            />
            {/* 게시글 삭제 버튼 */}
            <MiniButton text={'삭제하기'} onClick={onDeleteArticle} />
          </form>
        </div>
        {/* 게시글 상세 정보 */}
        <div className="content-detail">
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
      <LeftRightWholeConatiner>
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
