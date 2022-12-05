import styled from 'styled-components';
import Backbutton from '../components/BackButton';
import InputTitle from '../components/InputTitle';
import SkillStackSelect from '../components/SkillStackSelect';
import InterestSelect from '../components/InterestSelect';
import Calendar from '../components/Calendar';
import NumberSelect from '../components/NumberSelect';
import InputHashTag from '../components/InputHashTag';
import InputBody from '../components/InputBody';
import MiniButton from '../components/MiniButton';
import submitFormatDate from '../utils/submitFormatDate';
import { useRecoilValue, useRecoilState, useSetRecoilState } from 'recoil';
import {
  beNumberState,
  currentUserState,
  startDateState,
  endDateState,
  feNumberState,
  inputHashTagsState,
  selectedInterestsState,
  selectedSkillstacksState,
  inputTitleState,
  inputBodyState,
  inputTitleCheckState,
  inputBodyCheckState,
  interestsCheckState,
  skillstacksCheckState,
  feNumberCheckState,
  beNumberCheckState,
  startDateCheckState,
  endDateCheckState,
  hashtagsCheckState,
  nextState,
} from '../atom/atom';
import axios from 'axios';
import { useEffect } from 'react';
import { notiError, notiSuccess } from '../assets/toast';

const Container = styled.div`
  background-color: var(--purple-light);
  /* border: 1px solid red; */
  min-height: calc(100vh - 62px); //전체화면-헤더 높이
  width: 100%;
  display: flex;

  .body-area {
    background-color: var(--purple-light);
  }

  .article-write-title {
    margin: 30px 0 30px 0;
    padding-bottom: 30px;
    border-bottom: 1px solid var(--purple-medium);

    display: flex;
  }

  .article-write-body {
    width: 100%;
    min-width: fit-content;
    display: flex;
    flex-direction: row;
    margin-bottom: 30px;

    @media screen and (max-width: 1200px) {
      flex-direction: column;
    }

    .article-write-body-select {
      border-radius: 8px;
      background-color: white;
      width: 50%;
      min-width: 470px;
      height: 100%;
      padding: 15px;
      margin-right: 20px;

      display: flex;
      flex-direction: column;

      @media screen and (max-width: 1200px) {
        width: 100%;
        margin-bottom: 10px;
      }

      .select-tag,
      .select-date-number {
        border: 1px solid var(--purple-medium);
        border-radius: 8px;
        padding: 15px;

        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
      }
      .select-tag {
        margin-bottom: 15px;
      }

      .select-tag > *,
      .select-date-number > * {
        margin-bottom: 30px;
      }
    }

    .title {
      font-size: 18px;
      font-weight: 500;
      color: var(--black);

      .warning {
        color: red;
        font-size: 13px;
        font-weight: normal;
      }
    }

    .article-write-body-input {
      border-radius: 8px;
      background-color: white;
      width: 100%;
      height: 100%;
      padding: 15px;

      display: flex;
      flex-direction: column;

      .input-body > *,
      .input-hash-tag > * {
        margin-bottom: 30px;
      }

      .input-body {
        border: 1px solid var(--purple-medium);
        border-radius: 8px;

        padding: 15px;
        margin-bottom: 15px;
      }

      .input-hash-tag {
        border: 1px solid var(--purple-medium);
        border-radius: 8px;
        padding: 15px;
      }
    }

    .article-write-body-right {
      width: 50%;
      height: fit-content;
      min-width: 480px;

      @media screen and (max-width: 1200px) {
        width: 100%;
        margin-left: 0px;
      }
    }
  }

  .article-write-btn {
    display: flex;
    justify-content: right;
    margin-top: 15px;
  }

  .btn {
    margin-left: -50px;
  }
`;

const ArticleWrite = () => {
  const currentUser = useRecoilValue(currentUserState);

  const [inputTitle, setInputTitle] = useRecoilState(inputTitleState);
  const [selectedSkillstacks, setSelectedSkillstacks] = useRecoilState(
    selectedSkillstacksState,
  );
  const [selectedInterests, setSelectedInterests] = useRecoilState(
    selectedInterestsState,
  );
  const [startDate, setStartDate] = useRecoilState(startDateState);
  const [endDate, setEndDate] = useRecoilState(endDateState);
  const [feNumber, setFeNumber] = useRecoilState(feNumberState);
  const [beNumber, setBeNumber] = useRecoilState(beNumberState);
  const [inputBody, setInputBody] = useRecoilState(inputBodyState);
  const [inputHashTags, setInputHashTags] = useRecoilState(inputHashTagsState);

  const setInputTitleCheck = useSetRecoilState(inputTitleCheckState);
  const [interestsCheck, setInterestsCheck] =
    useRecoilState(interestsCheckState);
  const [skillstacksCheck, setSkillstacksCheck] = useRecoilState(
    skillstacksCheckState,
  );
  const [startDateCheck, setStartDateCheck] =
    useRecoilState(startDateCheckState);
  const [endDateCheck, setEndDateCheck] = useRecoilState(endDateCheckState);
  const [feNumberCheck, setFeNumberCheck] = useRecoilState(feNumberCheckState);
  const [beNumberCheck, setBeNumberCheck] = useRecoilState(beNumberCheckState);
  const [inputBodyCheck, setInputBodyCheck] =
    useRecoilState(inputBodyCheckState);
  const [hashtagsCheck, setHashtagsCheck] = useRecoilState(hashtagsCheckState);
  const [next, setNext] = useRecoilState(nextState);

  let selectedSkillstacksSubmit,
    selectedInterestsSubmit = [];

  useEffect(() => {
    setInputTitle('');
    setSelectedSkillstacks([]);
    setSelectedInterests([]);
    setStartDate(null);
    setEndDate(null);
    setFeNumber(0);
    setBeNumber(0);
    setInputBody('');
    setNext(next + 1);
    setInputHashTags([]);
  }, []);

  const onWriteSubmit = () => {
    // 선택된 기술 스택 목록을 POST 데이터 형식으로 변경
    selectedSkillstacksSubmit = selectedSkillstacks.map((el) => {
      return { skillName: el.name };
    });

    // 선택된 관심 분야 목록을 POST 데이터 형식으로 변경
    selectedInterestsSubmit = selectedInterests.map((el) => {
      return { interestName: el };
    });

    // 전부 다 작성되어야 axios POST
    if (onCheck() === true) {
      let writeBody = {
        memberId: currentUser.memberId,
        title: inputTitle,
        body: inputBody,
        startDay: submitFormatDate(startDate),
        endDay: submitFormatDate(endDate),
        backend: beNumber,
        frontend: feNumber,
        articleHashtags: inputHashTags,
        articleInterests: selectedInterestsSubmit,
        articleSkills: selectedSkillstacksSubmit,
      };

      axios
        .post(`/articles`, writeBody, {
          headers: { Authorization: localStorage.getItem('Authorization') },
        })
        .then(
          // 글 등록 후 해당 글 상세페이지로 이동
          (res) => {
            notiSuccess('글이 등록되었습니다.');
            window.location = `/articles/${res.data.data.articleId}`;
          },
        );
    }
  };

  const onCheck = () => {
    if (
      inputTitle !== '' &&
      inputBody !== '' &&
      submitFormatDate(startDate).length === 8 &&
      submitFormatDate(endDate).length === 8 &&
      beNumber > 0 &&
      feNumber > 0 &&
      inputHashTags.length > 0 &&
      selectedInterestsSubmit.length > 0 &&
      selectedSkillstacksSubmit.length > 0
    ) {
      return true;
    } else {
      if (inputTitle === '') {
        setInputTitleCheck(false);
      }
      if (inputBody === '') {
        setInputBodyCheck(false);
      }
      if (submitFormatDate(startDate).length !== 8) {
        setStartDateCheck(false);
      }
      if (submitFormatDate(endDate).length !== 8) {
        setEndDateCheck(false);
      }
      if (beNumber === 0) {
        setBeNumberCheck(false);
      }
      if (feNumber === 0) {
        setFeNumberCheck(false);
      }
      if (inputHashTags.length === 0) {
        setHashtagsCheck(false);
      }
      if (selectedInterestsSubmit.length === 0) {
        setInterestsCheck(false);
      }
      if (selectedSkillstacksSubmit.length === 0) {
        setSkillstacksCheck(false);
      }
      notiError('모두 입력해주세요!');
    }
  };

  return (
    <>
      <Container>
        <div className="body-area">
          <div className="article-write-title">
            <div className="btn">
              <Backbutton />
            </div>
            <InputTitle
              placeholder={'제목을 입력해주세요.'}
              value={inputTitle}
              onChange={setInputTitle}
            />
          </div>
          <div className="article-write-body">
            <div className="article-write-body-select">
              <div className="select-tag">
                <div className="title">
                  사용할 기술 스택을 태그로 표현해주세요
                  {skillstacksCheck ? (
                    ''
                  ) : (
                    <div className="warning">1개 이상 선택해주세요</div>
                  )}
                </div>
                <SkillStackSelect />
                <div className="title">
                  관심 분야를 선택해주세요
                  {interestsCheck ? (
                    ''
                  ) : (
                    <div className="warning">1개 이상 선택해주세요</div>
                  )}
                </div>
                <InterestSelect />
              </div>
              <div className="select-date-number">
                <div className="title">
                  프로젝트 예정 기간은 언제인가요?
                  {startDateCheck === true && endDateCheck === true ? (
                    ''
                  ) : (
                    <div className="warning">날짜를 선택해주세요</div>
                  )}
                </div>
                <Calendar />
                <div className="title">
                  파트별 인원 수를 설정해주세요
                  {feNumberCheck === false || beNumberCheck === false ? (
                    <div className="warning">
                      각 분야별 1명 이상 선택해주세요
                    </div>
                  ) : (
                    ''
                  )}
                </div>
                <NumberSelect />
              </div>
            </div>
            <div className="article-write-body-right">
              <div className="article-write-body-input">
                <div className="input-body">
                  <div className="title">
                    프로젝트 계획을 설명해 주세요!
                    {inputBodyCheck ? (
                      ''
                    ) : (
                      <div className="warning">내용을 입력해주세요</div>
                    )}
                  </div>
                  <InputBody />
                </div>
                <div className="input-hash-tag">
                  <div className="title">
                    해시태그를 입력해주세요
                    {hashtagsCheck ? (
                      ''
                    ) : (
                      <div className="warning">1개 이상 입력해주세요</div>
                    )}
                  </div>
                  <InputHashTag />
                </div>
              </div>
              <div className="article-write-btn">
                <MiniButton text="등록하기" onClick={onWriteSubmit} />
              </div>
            </div>
          </div>
        </div>
      </Container>
    </>
  );
};

export default ArticleWrite;
