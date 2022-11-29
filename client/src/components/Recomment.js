const Recomment = ({ recommentData, avatar, onDeleteComment, comment }) => {
  return (
    <>
      {recommentData.map((recomment) => (
        <div key={recomment.createdAt} className="recomment-form">
          <div className="user recomment-user">
            <div className="user-info">
              <img src={avatar} alt="" />
              <span className="user-name">{recomment.memberName}</span>
              <span className="comment-created">
                {new Date(recomment.createdAt).toLocaleString()}
              </span>
            </div>
            <span className="comment-btn">
              <button>수정하기</button>
              <button onClick={onDeleteComment}>삭제하기</button>
            </span>
          </div>
          <span className="user-name recomment-user-name">
            @ {comment.memberName}
          </span>
          <span className="recomment">{recomment.body}</span>
        </div>
      ))}
    </>
  );
};

export default Recomment;
