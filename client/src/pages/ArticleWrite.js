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
} from '../atom/atom';
import axios from 'axios';

const Container = styled.div`
  background-color: var(--purple-light);
  /* border: 1px solid red; */
  min-height: calc(100vh - 62px); //전체화면-헤더 높이
  width: 100%;
  display: flex;

  .article-write-title {
    margin: 30px 0 30px 0;
    padding-bottom: 30px;
    padding-right: 15px;
    border-bottom: 1px solid var(--purple-medium);

    display: flex;
  }

  .article-write-body {
    width: 100%;
    /* min-width: 930px; */
    display: flex;
    flex-direction: row;
    justify-content: space-around;
    margin-bottom: 30px;

    .article-write-body-select {
      border-radius: 8px;
      background-color: white;
      width: 47.5%;
      min-width: 470px;
      height: 100%;
      padding: 15px;
      margin-right: 5px;

      display: flex;
      flex-direction: column;

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
      width: 47.5%;
      height: fit-content;
      min-width: 480px;
      margin-left: 5px;
    }
  }

  .article-write-btn {
    display: flex;
    justify-content: right;
    margin-top: 15px;
  }

  .btn {
    /* margin-top: 30px; */
    margin-right: 10px;
  }
`;

const ArticleWrite = () => {
  const currentUser = useRecoilValue(currentUserState);

  const [inputTitle, setInputTitle] = useRecoilState(inputTitleState);
  const selectedSkillstacks = useRecoilValue(selectedSkillstacksState);
  const selectedInterests = useRecoilValue(selectedInterestsState);
  const startDate = useRecoilValue(startDateState);
  const endDate = useRecoilValue(endDateState);
  const feNumber = useRecoilValue(feNumberState);
  const beNumber = useRecoilValue(beNumberState);
  const inputBody = useRecoilValue(inputBodyState);
  const inputHashTags = useRecoilValue(inputHashTagsState);

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

  let selectedSkillstacksSubmit,
    selectedInterestsSubmit = [];

  // 객체로 저장된 날짜형식을 yyyymmdd의 포멧으로 변경
  const getFormatDate = (date) => {
    date = String(date);
    date = new Date(date);
    let year = date.getFullYear(); //yyyy
    let month = 1 + date.getMonth(); //M
    month = month >= 10 ? month : '0' + month; //month 두자리로 저장
    let day = date.getDate(); //d
    day = day >= 10 ? day : '0' + day; //day 두자리로 저장
    return `${year + '' + month + '' + day}`; //yyyymmdd
  };

  const onWriteSubmit = () => {
    // 선택된 기술 스택 목록을 POST 데이터 형식으로 변경
    selectedSkillstacksSubmit = selectedSkillstacks.map((el) => {
      let obj = { skillName: el.name };
      return obj;
    });

    // 선택된 관심 분야 목록을 POST 데이터 형식으로 변경
    selectedInterestsSubmit = selectedInterests.map((el) => {
      let obj = { interestName: el };
      return obj;
    });

    // 전부 다 작성되어야 axios POST
    if (onCheck() === true) {
      let writeBody = {
        memberId: currentUser.memberId,
        title: inputTitle,
        body: inputBody,
        startDay: getFormatDate(startDate),
        endDay: getFormatDate(endDate),
        backend: beNumber,
        frontend: feNumber,
        articleHashtags: inputHashTags,
        articleInterests: selectedInterestsSubmit,
        articleSkills: selectedSkillstacksSubmit,
      };

      let token = '';
      // 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi7YyM656R7J20IiwibWVtYmVySWQiOjE1LCJzdWIiOiJibHVlQGdtYWlsLmNvbSIsImlhdCI6MTY2OTQwMTc3NCwiZXhwIjoxNjY5NDE2MTc0fQ.bTiBx9EtXIfIMiPTsv-btdI_7FmM0ewNn9anl-16QQSjtrKIWDEtVKHChYfnaoDn';
      axios
        .post(`/articles`, writeBody, {
          headers: { Authorization: token },
        })
        .then(
          // 글 등록 후 해당 글 상세페이지로 이동
          (res) => (window.location = `/articles/${res.data.data.articleId}`),
        );
    }
  };

  const onCheck = () => {
    if (
      inputTitle !== '' &&
      inputBody !== '' &&
      getFormatDate(startDate).length === 8 &&
      getFormatDate(endDate).length === 8 &&
      beNumber > 0 &&
      feNumber > 0 &&
      inputHashTags.length > 0 &&
      selectedInterestsSubmit.length > 0 &&
      selectedSkillstacksSubmit.length > 0
    ) {
      return true;
    } else {
      let warnArr = [];

      if (inputTitle === '') {
        warnArr.push('제목을 작성해주세요.\n');
        setInputTitleCheck(false);
      }
      if (inputBody === '') {
        warnArr.push('본문을 작성해주세요.\n');
        setInputBodyCheck(false);
      }
      if (getFormatDate(startDate).length !== 8) {
        warnArr.push('시작 예정일을 선택해주세요.\n');
        setStartDateCheck(false);
      }
      if (getFormatDate(endDate).length !== 8) {
        warnArr.push('마감 예정일을 선택해주세요.\n');
        setEndDateCheck(false);
      }
      if (beNumber === 0) {
        warnArr.push('백엔드 인원을 선택해주세요.\n');
        setBeNumberCheck(false);
      }
      if (feNumber === 0) {
        warnArr.push('프론트엔드 인원을 선택해주세요.\n');
        setFeNumberCheck(false);
      }
      if (inputHashTags.length === 0) {
        warnArr.push('해시 태그를 입력해주세요.\n');
        setHashtagsCheck(false);
      }
      if (selectedInterestsSubmit.length === 0) {
        warnArr.push('관심 분야를 선택해주세요.\n');
        setInterestsCheck(false);
      }
      if (selectedSkillstacksSubmit.length === 0) {
        warnArr.push('기술 태그를 선택해주세요.\n');
        setSkillstacksCheck(false);
      }
      alert(warnArr.join(''));
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
              onblur={onblur}
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
                      <div className="warning">1자 이상 입력해주세요</div>
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
