/* eslint-disable react/no-unknown-property */
/* eslint-disable no-unused-vars */
/* eslint-disable jsx-a11y/label-has-associated-control */
import styled from 'styled-components';
import { useState } from 'react';
import axios from 'axios';
import DefaultButton from '../components/DefaultButton';
import InterestSelect from '../components/InterestSelect';
import LevelSelect from '../components/LevelSelect';
import MiniButton from '../components/MiniButton';
import SkillStackSelect from '../components/SkillStackSelect';
import Message from '../components/Message';
import Label from '../components/Label';
import LineInput from '../components/LineInput';
import { AiOutlineCheck } from 'react-icons/ai';
import { useRecoilState, useRecoilValue } from 'recoil';
import {
  selectedLevelState,
  selectedSkillstacksState,
  selectedInterestsState,
  skillstacksCheckState,
  interestsCheckState,
} from '../atom/atom';
import { notiError, notiInfo, notiSuccess } from '../assets/toast';

const Container = styled.div`
  min-height: calc(100vh - 62px);
  width: 100%;

  h1 {
    padding: 67px 0 77px;
    text-align: center;
  }

  .form-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
    grid-template-rows: auto;
    gap: 100px;

    .rightside {
      display: flex;
      flex-direction: column;
      justify-content: space-between;
    }

    .input-wrapper {
      display: flex;

      button,
      .confirmed-icon {
        margin-left: 25px;
      }

      .confirmed-icon {
        padding-top: 5px;
      }
    }

    .select-section {
      margin-bottom: 30px;

      .option-notice {
        font-size: 13px;
        font-weight: 400;
      }
    }
  }

  .submit-btn {
    margin-top: 60px;
    float: right;
  }

  .github-url {
    width: 100%;
    color: var(--black);
    line-height: 10px;
    padding-top: 12px;
    border-bottom: 1px solid var(--purple);
    font-size: 14px;
  }
`;

const SignUp = () => {
  const [userId, setUserId] = useState('');
  const [userIdErr, setUserIdErr] = useState(null); // id 에러(유효성 검사 미통과)
  const [idIsChecked, setIdChecked] = useState(false); // id 중복검사 + 코드전송 성공

  const [verificationCode, setVerificationCode] = useState('');
  const [verificationCodeErr, setVerificationCodeErr] = useState(null);
  const [codeIsChecked, setCodeChecked] = useState(false); // 코드 인증 성공

  const [nickName, setNickName] = useState('');
  const [nickNameErr, setNickNameErr] = useState(null);
  const [nameIsChecked, setNameChecked] = useState(false); // 닉네임 중복 검사 성공

  const [password, setPassword] = useState('');
  const [passwordErr, setPasswordErr] = useState(null);

  const [passwordCheck, setPasswordCheck] = useState('');
  const [passwordCheckErr, setPasswordCheckErr] = useState(null);

  const selectedLevel = useRecoilValue(selectedLevelState);
  const [levelErr, setLevelErr] = useState(false);

  const selectedSkillStacks = useRecoilValue(selectedSkillstacksState);
  const [skillstacksCheck, setSkillstacksCheck] = useRecoilState(
    skillstacksCheckState,
  );

  const selectedInterest = useRecoilValue(selectedInterestsState);
  const [interestsCheck, setInterestsCheck] =
    useRecoilState(interestsCheckState);

  const [github, setGithub] = useState('');
  const [githubIsChecked, setGithubChecked] = useState(false);

  // 내용 수정
  // 아이디
  const onIdChange = (id) => {
    setUserId(id);
    if (idIsChecked) {
      onIdValidation();
      setIdChecked(false);
      setVerificationCode('');
      setCodeChecked(false);
    }
  };
  // 닉네임
  const onNameChange = (name) => {
    setNickName(name);
    if (nameIsChecked) {
      onNickNameValidation();
      setNameChecked(false);
    }
  };
  // 비밀번호
  const onPasswordChange = (pw) => {
    setPassword(pw);
    if (pw.length >= 8) onPasswordValidation();
  };

  // 유효성 검사
  // 아이디
  const onIdValidation = () => {
    const email = new RegExp('[a-z0-9]+@[a-z]+.[a-z]{2,3}');
    setUserIdErr(!email.test(userId));
  };
  // 인증코드
  const onVerificationValidation = () => {
    setVerificationCodeErr(!verificationCode.length);
  };
  // 닉네임
  const onNickNameValidation = () => {
    setNickNameErr(!(nickName.length >= 1));
  };
  // 비밀번호
  const onPasswordValidation = () => {
    const regex =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
    setPasswordErr(!regex.test(password));
  };
  // 비밀번호 확인
  const onPasswordCheckValidation = () => {
    setPasswordCheckErr(passwordErr && password !== passwordCheck);
  };

  // 인증/확인 버튼
  // 아이디 인증하기
  const onCheckIdBtn = () => {
    onIdValidation();
    if (!userIdErr) {
      axios
        .post(`/members/signup/email-send`, {
          email: userId,
        })
        .then((res) => {
          notiSuccess(res.data.data);
          setIdChecked(true);
        })
        .catch((err) => {
          if (err.response.data.status === 409) {
            notiError(err.response.data.message);
          } else {
            console.log(err);
          }
        });
    } else {
      notiError('이메일 주소를 다시 확인해주세요');
    }
  };
  // 인증코드 인증확인
  const onCheckVerificationBtn = () => {
    axios
      .post(`/members/signup/email-auth`, {
        email: userId,
        code: verificationCode,
      })
      .then((res) => {
        notiSuccess(res.data.data);
        setVerificationCodeErr(false);
        setCodeChecked(true);
      })
      .catch(() => {
        notiError('인증코드가 유효하지 않습니다.');
        setVerificationCodeErr(true);
        setCodeChecked(false);
      });
  };
  // 닉네임 중복확인
  const onCheckNickNameBtn = () => {
    axios
      .post(`/members/signup/verify-name`, {
        name: nickName,
      })
      .then((res) => {
        notiSuccess('사용 가능한 닉네임 입니다');
        setNameChecked(true);
      })
      .catch((err) => {
        notiError(err.response.data.message);
      });
  };
  // 깃허브 연동하기
  const onConnectGithub = () => {
    const githubPopup = window.open(
      process.env.REACT_APP_OAUTH_GITHUB_URL,
      '깃허브 인증창',
      'width=600px,height=500px,scrollbars=yes',
    );
    githubPopup.addEventListener('unload', () => {
      const githubURL = window.localStorage.getItem('githubURL');
      if (githubURL) {
        notiInfo('깃허브 계정이 연동되었습니다!');
        setGithub(githubURL);
        setGithubChecked(true);
      }
    });
  };
  // 깃허브 연동 해제
  const onDisconnectGithub = () => {
    window.localStorage.removeItem('githubURL');
    setGithub('');
    setGithubChecked(false);
  };
  // 전체 내용 확인
  const onCheckAll = () => {
    onIdValidation();
    onNickNameValidation();
    onPasswordValidation();
    onPasswordCheckValidation();

    setSkillstacksCheck(selectedSkillStacks.length >= 1);
    setInterestsCheck(selectedInterest.length >= 1);
    setLevelErr(selectedLevel == null);
  };
  // 회원 가입
  const onSignUp = () => {
    onCheckAll();

    if (
      idIsChecked &&
      codeIsChecked &&
      nameIsChecked &&
      !passwordErr &&
      !passwordCheckErr &&
      !levelErr &&
      skillstacksCheck &&
      interestsCheck
    ) {
      const memberSkills = selectedSkillStacks.map((el) => {
        return { skillName: el.name };
      });

      const memberInterests = selectedInterest.map((el) => {
        return { interestName: el };
      });

      const signUpInfo = {
        email: userId,
        name: nickName,
        password,
        passwordCheck,
        level: selectedLevel,
        memberInterests,
        memberSkills,
      };
      if (github.length > 1) signUpInfo.github = github;

      axios
        .post('/members/signup', signUpInfo)
        .then(() => {
          // + 입력되어 있던 데이터로 로그인 요청
          notiSuccess('가입되었습니다'); // 로그인 요청 추가시 환영 문구로 변경 필요
          window.location = '/';
          window.localStorage.removeItem('githubURL');
        })
        .catch((err) => {
          if (err.response.data.status === 409) {
            notiError(err.response.data.message);
          }
          console.error(err);
        });
    } else {
      notiError('입력 사항을 확인해주세요!');
    }
  };

  return (
    <Container>
      <h1>회원가입</h1>
      <div className="form-grid">
        <div className="leftside">
          <Label htmlFor="user-id">아이디</Label>
          <div className="input-wrapper">
            <LineInput
              id="user-id"
              message={
                idIsChecked
                  ? '이메일 주소를 다시 확인해주세요.'
                  : '아이디로 사용될 이메일을 입력해주세요.'
              }
              value={userId}
              onChange={onIdChange}
              onBlur={onIdValidation}
              isError={userIdErr}
            />
            {!idIsChecked ? (
              <MiniButton text="인증하기" onClick={onCheckIdBtn} />
            ) : (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="verification-code">인증코드</Label>
          <div className="input-wrapper">
            <LineInput
              id="verification-code"
              message={
                idIsChecked
                  ? '이메일로 전송된 코드를 입력해주세요.'
                  : '이메일 인증을 먼저 진행해주세요.'
              }
              value={verificationCode}
              onChange={setVerificationCode}
              onBlur={onVerificationValidation}
              isError={verificationCodeErr}
              disabled={!idIsChecked}
            />
            {!codeIsChecked ? (
              <MiniButton
                text="인증확인"
                onClick={onCheckVerificationBtn}
                disabled={!idIsChecked}
              />
            ) : (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="nickname">닉네임</Label>
          <div className="input-wrapper">
            <LineInput
              id="nickname"
              message={
                nickNameErr ? '1자 이상 입력해주세요.' : '이름을 입력해주세요.'
              }
              value={nickName}
              onChange={onNameChange}
              onBlur={onNickNameValidation}
              isError={nickNameErr}
            />
            {!nameIsChecked ? (
              <MiniButton text="중복확인" onClick={onCheckNickNameBtn} />
            ) : (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <Label htmlFor="password">비밀번호</Label>
          <LineInput
            id="password"
            message={
              passwordErr
                ? '영문, 숫자, 특수문자를 포함해 8자 이상 입력해주세요.'
                : '8글자 이상의 영문, 숫자, 특수문자 조합이어야 합니다.'
            }
            value={password}
            onChange={onPasswordChange}
            onBlur={onPasswordValidation}
            isError={passwordErr}
            type="password"
          />
          <Label htmlFor="password-check">비밀번호 확인</Label>
          <LineInput
            id="password-check"
            message={
              passwordCheckErr
                ? '비밀번호가 일치하지 않습니다.'
                : '비밀번호를 다시 한 번 입력해주세요.'
            }
            value={passwordCheck}
            onChange={setPasswordCheck}
            onBlur={onPasswordCheckValidation}
            isError={passwordCheckErr}
            disabled={password.length <= 8 || passwordErr}
            type="password"
          />
          <Label htmlFor="level">숙련도</Label>
          <LevelSelect id="level" />
          {levelErr && <Message isError={levelErr} text="필수 입력입니다." />}
        </div>
        <div className="rightside">
          <div className="select-section">
            <Label htmlFor="skill-stack">
              사용해본 기술 스택을 선택해주세요
            </Label>
            {!skillstacksCheck && (
              <Message isError={true} text="1개 이상 선택해주세요." />
            )}
            <SkillStackSelect id="skill-stack" />
          </div>
          <div className="select-section">
            <Label htmlFor="interest">관심 분야를 선택해주세요</Label>
            {!interestsCheck && (
              <Message isError={true} text="1개 이상 선택해주세요." />
            )}
            <InterestSelect id="interest" />
          </div>
          <div className="select-section">
            <Label htmlFor="github">
              깃허브<span className="option-notice">(선택)</span>
            </Label>
            <div className="input-wrapper">
              <p className="github-url" id="github">
                {githubIsChecked ? github : '깃허브 계정을 연동해주세요!'}
              </p>
              {githubIsChecked ? (
                <>
                  <div className="confirmed-icon">
                    <AiOutlineCheck fill="var(--purple)" />
                  </div>
                  <MiniButton
                    text="해제하기"
                    onClick={onDisconnectGithub}
                    color="var(--purple-light)"
                    bgColor="var(--purple)"
                  />
                </>
              ) : (
                <MiniButton text="연동하기" onClick={onConnectGithub} />
              )}
            </div>
          </div>
        </div>
      </div>
      <DefaultButton
        text="가입하기"
        className="submit-btn"
        onClick={onSignUp}
      />
    </Container>
  );
};

export default SignUp;
