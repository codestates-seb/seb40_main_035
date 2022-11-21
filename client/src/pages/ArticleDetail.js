import { useState } from 'react';
import styled from 'styled-components';
import Backbutton from '../components/BackButton';
import MiniButton from '../components/MiniButton';
import SwitchToggle from '../components/SwitchToggle';
import SkillStackView from '../components/SkillStackView';
import InterestSelect from '../components/InterestSelect';

const WholeContainer = styled.div`
  background-color: var(--purple-light);
  height: 100vh;
`;
const TopContainer = styled.div``;
const LeftViewContainer = styled.div``;
const LeftViewTopBox = styled.div``;
const LeftViewBottomBox = styled.div``;
const RightViewContainer = styled.div``;
const BottomCommentConatiner = styled.div``;
const CommentBox = styled.form``;
const CommnetWriteBox = styled.form``;

const ArticleDetail = () => {
  const [isCheck, setIsCheck] = useState(true);
  return (
    <WholeContainer>
      <TopContainer>
        <Backbutton />
        <span>프로젝트 제목</span>
        <div>
          <SwitchToggle
            right="모집 완료"
            setChecked={isCheck}
            onClick={() => {
              setIsCheck(!isCheck);
            }}
          />
        </div>
        <MiniButton text={'수정하기'} />
        <MiniButton text={'삭제하기'} />
      </TopContainer>
      <div>
        <LeftViewContainer>
          <LeftViewTopBox>
            <div>이런 기술 스택을 사용하고 싶어요.</div>
            <SkillStackView />
            <div>이런 산업군에 관심이 있어요.</div>
            <InterestSelect />
          </LeftViewTopBox>
          <LeftViewBottomBox>
            <ul>
              프로젝트 예정기간
              <li>시작 예정 일</li>
              <li>마감 예정 일</li>
            </ul>
            <ul>
              파트별 인원 수<li>프론트엔드</li>
              <li>백엔드</li>
            </ul>
            <ul>
              이런 분과 함께 하고 싶어요.
              <li>학생,취준생</li>
            </ul>
          </LeftViewBottomBox>
        </LeftViewContainer>
        <RightViewContainer>
          <div>프로젝트 내용을 설명해 주세요!</div>
          <div>프로젝트 내용 설명</div>
        </RightViewContainer>
      </div>
      <BottomCommentConatiner>
        <div>개의 댓글이 있습니다.</div>
        <CommentBox>
          <div className="userInfo">
            <div className="user-profile-img" />
            <div className="user-name">김코딩</div>
            <div className="comment-created-at">22.11.1</div>
          </div>
          <div className="comment-btn">
            <button>수정하기</button>
            <button>삭제하기</button>
          </div>
          <div>댓글 내용입니다.</div>
        </CommentBox>

        <CommnetWriteBox>
          <input placeholder="댓글을 입력하세요."></input>
          <button>입력</button>
        </CommnetWriteBox>
      </BottomCommentConatiner>
    </WholeContainer>
  );
};

export default ArticleDetail;
