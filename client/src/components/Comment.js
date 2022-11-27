/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import { BsArrowUpCircleFill, BsHeartFill } from 'react-icons/bs';
import Recomment from './Recomment';
import { useState } from 'react';

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
  const [edited, setEdited] = useState(false);
  const onClickEditButton = () => {
    // 클릭시 edited 값을 true로 바꿈
    setEdited(true);
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
                <span className="user-name">{comment.memberName}</span>
                <span className="comment-created">
                  {new Date(comment.createdAt).toLocaleString()}
                </span>
              </div>
              <span className="comment-btn">
                <button>수정하기</button>
                <button value={comment.answerId} onClick={onDeleteComment}>
                  삭제하기
                </button>
              </span>
            </div>
            <span className="comment">{comment.body}</span>
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
