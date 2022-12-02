import styled from 'styled-components';
import Recomment from './Recomment';
import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const CommentBox = styled.form`
  font-size: 14px;
  display: flex;
  flex-direction: column;
  width: 75%;
  min-width: fit-content;
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
    width: 100%;
    padding: 15px;
    margin-top: 10px;
    background-color: var(--purple-light);
    border-radius: 8px;
    &:hover,
    &:focus,
    &:active {
      border: 2px solid var(--purple-medium);
    }
  }
  .answer-form {
    margin-top: 15px;
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
const Comment = ({ avatar, answers, onDeleteComment }) => {
  const [editIng, setEditIng] = useState(false);
  const [editSelectedIdx, setEditSelectedIdx] = useState();
  const [editInput, setEditInput] = useState('');
  const navigate = useNavigate();

  // 댓글 수정하기 이벤트 핸들러
  const onEditBtn = (e) => {
    e.preventDefault();
    setEditIng(true);
    setEditSelectedIdx(e.target.value);
    setEditInput(answers[e.target.value].body);
  };
  // 댓글 수정완료 이벤트 핸들러
  const onEditCompleteBtn = (e) => {
    e.preventDefault();
    setEditIng(false);
    setEditSelectedIdx(e.target.value);
    updateCommentSubmit(e.target.value);
  };

  const onChangeEdit = (e) => {
    e.preventDefault();
    setEditInput(e.target.value);
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
          // 로그인 토큰 자리
          Authorization: '',
        },
      },
    );
  };

  return (
    <CommentBox>
      {answers.map((comment, idx) => (
        <div key={idx}>
          {/* 댓글 컴포넌트 */}
          <div className="answer-form">
            <div className="user">
              <div className="user-info">
                <img src={avatar} alt="" />
                <button
                  onClick={() => {
                    navigate(`/profile/${comment.memberId}`);
                  }}
                  className="user-name"
                >
                  {comment.memberName}
                </button>
                <span className="comment-created">
                  {new Date(comment.createdAt).toLocaleString()}
                </span>
              </div>
              <span className="comment-btn">
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
        </div>
      ))}
    </CommentBox>
  );
};

export default Comment;
