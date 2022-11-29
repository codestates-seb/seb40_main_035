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
import { AiOutlineCheck } from 'react-icons/ai';
import { useRecoilState, useRecoilValue } from 'recoil';
import {
  selectedLevelState,
  selectedSkillstacksState,
  selectedInterestsState,
  skillstacksCheckState,
  interestsCheckState,
} from '../atom/atom';

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

    .select-section {
      margin-bottom: 30px;
    }

    .github-link {
      display: flex;
      align-items: center;

      span {
        font-size: 13px;
        font-weight: 400;
      }
    }

    label {
      display: inline-block;
      width: 100%;
      font-size: 18px;
      font-weight: 500;
      color: var(--black);
      margin-bottom: 5px;
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
  }

  .submit-btn {
    margin-top: 60px;
    float: right;
  }
`;

const FormInputContainer = styled.div`
  width: 100%;
  margin-bottom: 30px;

  &.disabled {
    label,
    span {
      color: grey;
    }

    input {
      border-color: grey;
    }
  }

  input {
    width: 100%;
    height: 36px;
    background-color: transparent;
    border: none;
    border-bottom: 1px solid var(--purple);

    &:focus {
      outline: none;
    }

    &.error {
      border-color: red;
    }
  }
`;

const Span = styled.span`
  font-size: 13px;
  color: var(--purple);

  &.error {
    color: red;
  }
`;

const Description = ({ isError, text }) => {
  return <Span className={isError ? 'error' : ''}>{text}</Span>;
};

const FormInput = ({
  id,
  label,
  description,
  value,
  onChange,
  errorMsg,
  isError,
  onBlur,
  type,
  disabled,
}) => {
  return (
    <FormInputContainer className={disabled ? 'disabled' : ''}>
      <div className="input-section">
        <input
          id={id}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          onBlur={onBlur}
          type={type === 'password' ? 'password' : 'text'}
          className={isError ? 'error' : ''}
          disabled={disabled}
        />
      </div>
      <Description
        isError={isError}
        text={isError === null ? description : isError ? errorMsg : ' '}
      />
    </FormInputContainer>
  );
};

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
          window.alert(res.data.data);
          setIdChecked(true);
        })
        .catch((err) => {
          if (err.response.data.status === 409) {
            window.alert(err.response.data.message);
          } else {
            console.log(err);
          }
        });
    } else {
      window.alert('이메일 주소를 다시 확인해주세요');
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
        window.alert(res.data.data);
        setVerificationCodeErr(false);
        setCodeChecked(true);
      })
      .catch(() => {
        window.alert('인증코드가 유효하지 않습니다.');
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
        window.alert('사용 가능한 닉네임 입니다');
        setNameChecked(true);
      })
      .catch((err) => {
        window.alert(err.response.data.message);
      });
  };
  // 깃허브 연동하기
  const onConnectGithub = () => {
    // axios.get(`/oauth2/authorization/github`).then((res) => console.log(res));
  };

  // 내용 확인
  const CheckAll = () => {
    onIdValidation();
    onNickNameValidation();
    onPasswordValidation();
    onPasswordCheckValidation();

    setSkillstacksCheck(selectedSkillStacks.length >= 1);
    setInterestsCheck(selectedInterest.length >= 1);
    setLevelErr(selectedLevel == null);
  };

  const onSignUp = () => {
    CheckAll();

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

      axios
        .post('/members/signup', signUpInfo)
        .then(() => {
          // + 입력되어 있던 데이터로 로그인 요청
          window.alert('가입되었습니다'); // 로그인 요청 추가시 환영 문구로 변경 필요
          window.location = '/';
        })
        .catch((err) => {
          if (err.response.data.status === 409) {
            window.alert(err.response.data.message);
          }
          console.error(err);
        });
    } else {
      window.alert('입력 사항을 확인해주세요!');
    }
  };

  return (
    <Container>
      <h1>회원가입</h1>
      <div className="form-grid">
        <div className="leftside">
          <label htmlFor="user-id">아이디</label>
          <div className="input-wrapper">
            <FormInput
              id="user-id"
              description="아이디로 사용될 이메일을 입력해주세요."
              errorMsg="이메일 주소를 다시 확인해주세요."
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
          <label htmlFor="verification-code">인증코드</label>
          <div className="input-wrapper">
            <FormInput
              id="verification-code"
              description={
                idIsChecked
                  ? '이메일로 전송된 코드를 입력해주세요.'
                  : '이메일 인증을 먼저 진행해주세요.'
              }
              value={verificationCode}
              onChange={setVerificationCode}
              errorMsg="인증 코드가 일치하지 않습니다."
              isError={verificationCodeErr}
              onBlur={onVerificationValidation}
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
          <label htmlFor="nickname">닉네임</label>
          <div className="input-wrapper">
            <FormInput
              id="nickname"
              description="이름을 입력해주세요."
              value={nickName}
              onChange={onNameChange}
              errorMsg="1자 이상 입력해주세요."
              isError={nickNameErr}
              onBlur={onNickNameValidation}
            />
            {!nameIsChecked ? (
              <MiniButton text="중복확인" onClick={onCheckNickNameBtn} />
            ) : (
              <div className="confirmed-icon">
                <AiOutlineCheck fill="var(--purple)" />
              </div>
            )}
          </div>
          <label htmlFor="password">비밀번호</label>
          <FormInput
            id="password"
            description="8글자 이상의 영문, 숫자, 특수문자 조합이어야 합니다."
            value={password}
            onChange={onPasswordChange}
            errorMsg="영문, 숫자, 특수문자를 포함해 8자 이상 입력해주세요."
            isError={passwordErr}
            onBlur={onPasswordValidation}
            type="password"
          />
          <label htmlFor="password-check">비밀번호 확인</label>
          <FormInput
            id="password-check"
            description="비밀번호를 다시 한 번 입력해주세요."
            errorMsg="비밀번호가 일치하지 않습니다."
            value={passwordCheck}
            onChange={setPasswordCheck}
            isError={passwordCheckErr}
            onBlur={onPasswordCheckValidation}
            type="password"
            disabled={password.length <= 8 || passwordErr}
          />
          <label htmlFor="level">숙련도</label>
          <LevelSelect id="level" />
          {levelErr && (
            <Description isError={levelErr} text="필수 입력입니다." />
          )}
        </div>
        <div className="rightside">
          <div className="select-section">
            <label htmlFor="skill-stack">
              사용해본 기술 스택을 선택해주세요
            </label>
            {!skillstacksCheck && (
              <Description isError={true} text="1개 이상 선택해주세요." />
            )}
            <SkillStackSelect id="skill-stack" />
          </div>
          <div className="select-section">
            <label htmlFor="interest">관심 분야를 선택해주세요</label>
            {!interestsCheck && (
              <Description isError={true} text="1개 이상 선택해주세요." />
            )}
            <InterestSelect id="interest" />
          </div>
          <div className="select-section github-link">
            <label htmlFor="github">
              깃허브<span>(선택)</span>
            </label>
            <MiniButton text="연동하기" onClick={onConnectGithub} />
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
