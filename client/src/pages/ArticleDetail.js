/* eslint-disable no-unused-vars */
import { useEffect, useState } from 'react';
import styled from 'styled-components';
import Backbutton from '../components/BackButton';
import MiniButton from '../components/MiniButton';
import SwitchToggle from '../components/SwitchToggle';
import SkillStackView from '../components/SkillStackView';
import avatar from '../assets/image/userAvatar.png';
import { BsArrowUpCircleFill, BsHeartFill, BsHeart } from 'react-icons/bs';
import { FiShare } from 'react-icons/fi';
import { Link, useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import {
  currentUserState,
  heartArticleState,
  heartMemberIdState,
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
  padding: 30px;
  margin: 30px 5px 25px 0;

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
  .project-schedule {
    display: flex;
    flex-direction: column;
  }
`;

const BottomCommentConatiner = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
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
  const [heartMemberId, setHeartMemberId] = useRecoilState(heartMemberIdState);
  const [heartArticle, setHeartArticle] = useRecoilState(heartArticleState);
  const [heartCount, setHeartCount] = useState(null);
  // ????????? ?????? http ??????
  useEffect(() => {
    axios.get(`/articles/${id}`).then((response) => {
      // ????????? viewer ?????? set
      setInputBody(response.data.data.body);
      // articles ?????? ????????? ??????
      setArticles(response.data.data);
      // ????????? Count ?????? set
      setHeartCount(response.data.data.heartCount);
      // ???????????? ?????? set
      setInterestView(response.data.data.interests);
      // ???????????? ?????? set
      setSkillStackView(getSkills(response.data.data.skills));
      // ?????? ?????? set
      setAnswers(response.data.data.answers);
      // ?????? ?????? ?????? set
      setIsCheck(response.data.data.isCompleted);
    });
  }, [newComment]);

  // ?????? ?????? ??????
  useEffect(() => {
    axios.get(`/members/${currentUser.memberId}`).then((res) => {
      setHeartMemberId(res.data.data.memberId);
      setHeartArticle(res.data.data.heartArticles);
      onCheckHeart(res.data.data.heartArticles);
    });
  }, []);

  const onCheckHeart = (res) => {
    const temp = res.map((el) =>
      String(el.articleId).includes(String(id)) ? 'o' : 'x',
    );

    if (temp.includes('o')) {
      setLiked(true);
    } else setLiked(false);
  };
  // ????????? ????????? ?????????
  const onHeartSubmit = (e) => {
    e.preventDefault();
    axios
      .patch(
        `/articles/${id}`,
        {
          hearts: [
            {
              // ????????? ????????? ?????? ?????? ??????
              memberId: currentUser.memberId,
            },
          ],
        },
        {
          headers: {
            Authorization: localStorage.getItem('Authorization'),
          },
        },
      )
      .then((response) => {
        setLiked(!liked);
        setHeartCount(response.data.data.heartCount);
      });
  };
  // ????????? ?????? ????????? ?????????
  const onDeleteArticle = (e) => {
    e.preventDefault();
    deleteArticleSubmit();
    navigate('/');
  };
  const deleteArticleSubmit = () => {
    axios.delete(`/articles/${id}`, {
      headers: {
        // ????????? ?????? ??????
        Authorization: localStorage.getItem('Authorization'),
      },
    });
  };
  const onToggleChange = () => {
    setIsCheck((isCheck) => !isCheck);
  };
  // ????????? ?????? ????????? ?????????
  const onToggle = () => {
    if (Number(currentUser.memberId) === articles.memberId) {
      axios
        .patch(
          `/articles/${id}`,
          {
            isCompleted: !isCheck,
          },
          {
            headers: {
              // ????????? ?????? ??????
              Authorization: localStorage.getItem('Authorization'),
            },
          },
        )
        .then((response) => {
          onToggleChange(isCheck);
        });
    } else {
      notiError('?????? ????????? ????????????.');
    }
  };

  // ?????? ?????? ????????? ?????????
  const onAnswerHandler = (e, articleId) => {
    if (currentUser.isLogIn) {
      e.preventDefault();
      let answerValue = e.target.value;
      setAnswerInput(answerValue);
      answerSubmit(articleId);
    } else {
      notiError('????????? ??? ?????? ????????? ?????????!');
    }
  };
  // ?????? Enter submit ????????? ??????
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
            // ????????? ?????? ??????
            Authorization: localStorage.getItem('Authorization'),
          },
        },
      )
      .then((response) => {
        setNewComment(!newComment);
        setAnswerInput('');
      })
      .catch(console.error);
  };
  // ?????? ?????? ????????? ?????????
  const onDeleteComment = (e) => {
    e.preventDefault();
    deleteCommentSubmit(e.target.value);
  };

  const deleteCommentSubmit = (answerId) => {
    axios
      .delete(`/answers/${answerId}`, {
        headers: {
          // ????????? ?????? ??????
          Authorization: localStorage.getItem('Authorization'),
        },
      })
      .then((response) => {
        setNewComment(!newComment);
      });
  };

  return (
    <WholeContainer>
      {/* ????????? ?????? */}
      <TopContainer className="abc">
        <div className="title-box">
          <div className="title-left-box">
            <Backbutton />
            <span>{articles.title} </span>
            <div className="toggle">
              <SwitchToggle
                right="?????? ??????"
                setChecked={isCheck}
                onClick={onToggle}
              />
            </div>
          </div>
          <form className="title-right-box">
            {/* ?????? ?????? */}
            <button className="title-btn">
              <span className="title-icons title-share-icons">
                <FiShare />
              </span>
            </button>
            {/* ????????? ?????? */}
            {liked ? (
              <button className="title-btn" onClick={onHeartSubmit}>
                <span className="title-icons title-heart-icons">
                  <BsHeartFill />
                </span>
              </button>
            ) : (
              <button className="title-btn" onClick={onHeartSubmit}>
                <span className="title-icons title-heart-icons">
                  <BsHeart />
                </span>
              </button>
            )}
            {/* ????????? ?????? ?????? */}

            {Number(currentUser.memberId) === articles.memberId ? (
              <>
                <MiniButton
                  onClick={() => {
                    navigate(`/articles/edit/${id}`);
                  }}
                  text={'????????????'}
                />
                {/* ????????? ?????? ?????? */}
                <MiniButton text={'????????????'} onClick={onDeleteArticle} />
              </>
            ) : (
              ''
            )}
          </form>
        </div>
        {/* ????????? ?????? ?????? */}
        <div className="content-detail">
          {/* ????????? ????????? */}
          {Number(currentUser.memberId) === articles.memberId ? (
            <Link to={`/mypage/${currentUser.memberId}`}>
              <span>{articles.memberName}</span>
            </Link>
          ) : (
            <Link to={`/profile/${articles.memberId}`}>
              <span>{articles.memberName}</span>
            </Link>
          )}
          {/* ????????? */}
          <span>{articles.views} view</span>
          {/* ????????? ??? */}
          <span>
            <BsHeartFill className="conetent-heart-icons" />
            {heartCount}
          </span>
          {/* ?????? ??? */}
          <span>????????? {createdAtArticle.toLocaleString()}</span>
        </div>
      </TopContainer>
      <LeftRightWholeConatiner className="abcde">
        <LeftViewContainer>
          <LeftViewTopBox>
            <span>?????? ?????? ????????? ???????????? ?????????.</span>
            <SkillStackView size="12px" />
            <InterestView size="15px"></InterestView>
          </LeftViewTopBox>
          <LeftViewBottomBox>
            <ul>
              ?????? ???????????? ????????? ?????????.
              <li>
                <span className="left-bottom-content">
                  {articles.memberLevel}
                </span>
              </li>
            </ul>
            <ul>
              ???????????? ????????????
              <div className="project-schedule">
                <li>
                  <span className="project-plan-title">?????? ?????? ???</span>
                  <span className="left-bottom-content">
                    {articles.startDay}
                  </span>
                </li>
                <li>
                  <span className="project-plan-title">?????? ?????? ???</span>
                  <span className="left-bottom-content">{articles.endDay}</span>
                </li>
              </div>
            </ul>
            <ul>
              ????????? ?????? ???
              <div>
                <li>
                  <span className="project-plan-title">??????????????? </span>
                  <span className="left-bottom-content">
                    {articles.frontend}
                  </span>
                </li>
                <li>
                  <span className="project-plan-title">????????? </span>
                  <span className="left-bottom-content">
                    {articles.backend}
                  </span>
                </li>
              </div>
            </ul>
          </LeftViewBottomBox>
        </LeftViewContainer>
        {/* ????????? Viewer */}
        <ContentViewer content={inputBody} />
      </LeftRightWholeConatiner>
      {/* ?????? ???????????? */}
      <BottomCommentConatiner>
        <div className="comment-count">
          <span>{articles.answerCount}?????? ????????? ????????????.</span>
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
            placeholder="????????? ???????????????."
            value={answerInput || ''} // ?????? ??? ???????????? undefined??? ?????? ????????? ??????????????? ????????? ???????????????.
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
