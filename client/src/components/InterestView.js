import styled from 'styled-components';
import { useRecoilValue } from 'recoil';
import { interestViewState } from '../atom/atom';
import {
  FcCurrencyExchange,
  FcSupport,
  FcFlashOn,
  FcShipped,
  FcVideoCall,
  FcLike,
  FcOrganization,
  FcGraduationCap,
  FcQuestions,
} from 'react-icons/fc';

const Container = styled.div`
  width: 100%;
  color: var(--black);
  font-size: 13px;

  .title {
    border-bottom: 1px solid var(--purple-medium);
    height: 50px; // 수정
    font-weight: 500;

    display: flex;
    flex-direction: row;
    justify-content: start;
    align-items: center;
  }

  .content {
    display: flex;
    flex-wrap: wrap;
  }

  .interest-tag {
    border: 1px solid var(--purple-medium);
    border-radius: 25px;
    background-color: white;
    font-size: 13px;

    display: flex;
    align-items: center;
    margin: 15px 8px -5px 0;
    padding: 5px 10px;
  }

  .interest-tag-img {
    width: 20px;
    height: 20px;

    margin-right: 6px;
    border-radius: 100%;
    border: 2px solid white;
    background-color: white;
  }

  .interest-selected-tag {
    border: 1px solid var(--purple);
    border-radius: 25px;
    background-color: var(--purple);
    color: white;

    display: flex;
    align-items: center;
    margin: 15px 7px 1px 0;
    padding: 7px 10px;
  }
`;

function InterestView() {
  const interestView = useRecoilValue(interestViewState);

  const interestContArr = [
    { name: '금융', img: <FcCurrencyExchange className="interest-tag-img" /> },
    { name: '제조', img: <FcSupport className="interest-tag-img" /> },
    { name: '에너지/친환경', img: <FcFlashOn className="interest-tag-img" /> },
    { name: '유통/물류', img: <FcShipped className="interest-tag-img" /> },
    { name: '미디어', img: <FcVideoCall className="interest-tag-img" /> },
    { name: '의료/헬스 케어', img: <FcLike className="interest-tag-img" /> },
    { name: '건설', img: <FcOrganization className="interest-tag-img" /> },
    { name: '교육', img: <FcGraduationCap className="interest-tag-img" /> },
    { name: '기타', img: <FcQuestions className="interest-tag-img" /> },
  ];

  return (
    <Container>
      <div className="title">관심 분야 (산업군)</div>
      <div className="content">
        {interestContArr.map((interestTag, idx) => {
          return (
            <div
              key={idx}
              className={
                interestView.filter((el) => el.name === interestTag.name)
                  .length !== 0
                  ? 'interest-tag interest-selected-tag'
                  : 'interest-tag'
              }
            >
              {interestTag.img}
              {interestTag.name}
            </div>
          );
        })}
      </div>
    </Container>
  );
}

export default InterestView;
