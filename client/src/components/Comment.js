// enter 기능
// 수정 완료 후 새로고침 안되게 수정
// 포커싱

/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import { BsArrowUpCircleFill, BsHeartFill } from 'react-icons/bs';
import Recomment from './Recomment';
import { useRef, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CommentBox = styled.form`
  font-size: 14px;
  display: flex;
  flex-direction: column;
  width: 75%;
  justify-content: center;
  /* align-items: flex-start; */
  padding-bottom: 20px;
  .user-name {
    font-size: 14px;
    font-weight: 500;
    color: var(--purple);
  }
  .recomment-user-name {
    margin-left: 35px;
  }
  .user {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 3px;
    .user-info {
      display: flex;
      align-items: center;
      font-size: 14px;
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
  .edit-input {
    border: solid 1px black;
  }
  .answer-form {
    margin-top: 15px;
    /* margin-bottom: 15px; */
  }

  .recomment-form {
    margin-top: 15px;
    margin-left: 75px;
  }
  .recomment {
    font-size: 13px;
    margin-left: 12px;
  }
`;
const Comment = ({
  avatar,
  articles,
  answers,
  onDeleteComment,
  onAnswerHandler,
  answerInput,
  onChangeAnswer,
}) => {
  const [editIng, setEditIng] = useState(false);
  const [editSelectedIdx, setEditSelectedIdx] = useState();
  const [editInput, setEditInput] = useState('');
  const navigate = useNavigate();

  const onEditBtn = (e) => {
    // 클릭시 edited 값을 true로 바꿈
    e.preventDefault();
    setEditIng(true);
    setEditSelectedIdx(e.target.value);
    setEditInput(answers[e.target.value].body);
  };

  const onEditCompleteBtn = (e) => {
    // e.preventDefault();
    setEditIng(false);
    setEditSelectedIdx(e.target.value);
    updateCommentSubmit(e.target.value);
  };

  const onChangeEdit = (e) => {
    e.preventDefault();
    setEditInput(e.currentTarget.value);
  };

  // 댓글 수정 이벤트 핸들러
  const updateCommentSubmit = (idx) => {
    axios.patch(
      `/answers/${answers[idx].answerId}`,
      {
        answerId: answers[idx].answerId,
        body: editInput,
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

  const onProfileClick = (answerId) => {
    // navigate(`/members/${answerId}`);
    console.log(answerId);
  };

  return (
    <CommentBox>
      {answers.map((comment, idx) => (
        <>
          {/* 댓글 컴포넌트 */}
          <div key={comment.createdAt} className="answer-form">
            <div className="user">
              <div className="user-info">
                <img src={avatar} alt="" />
                <button
                  onClick={onProfileClick(comment.answerId)}
                  className="user-name"
                >
                  {comment.memberName}
                </button>
                <span className="comment-created">
                  {new Date(comment.createdAt).toLocaleString()}
                </span>
              </div>
              <span className="comment-btn">
                {console.log(idx)}
                {editSelectedIdx == idx && editIng ? (
                  <button value={idx} onClick={onEditCompleteBtn}>
                    수정완료
                  </button>
                ) : (
                  <button value={idx} onClick={onEditBtn}>
                    수정하기
                  </button>
                )}
                <button value={comment.answerId} onClick={onDeleteComment}>
                  삭제하기
                </button>
              </span>
            </div>
            {editSelectedIdx == idx && editIng ? (
              <input
                type="text"
                // ref={(el) => (inputFocus.current[idx] = el)}
                className="edit-input"
                value={editInput}
                onChange={onChangeEdit}
              ></input>
            ) : (
              <span className="comment">{comment.body}</span>
            )}
          </div>
          {/* 대댓글 컴포넌트  */}
          {comment.comments.length >= 1 ? (
            <Recomment
              recommentData={comment.comments}
              comment={comment}
              avatar={avatar}
              onDeleteComment={onDeleteComment}
            />
          ) : null}
        </>
      ))}
    </CommentBox>
  );
};

export default Comment;
