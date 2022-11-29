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
    gap: 100px;

    .leftside,
    .rightside {
      width: 100%;
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

  span {
    font-size: 13px;
    color: var(--purple);

    &.error {
      color: red;
    }
  }

  &.disabled {
    label,
    span {
      color: grey;
    }

    input {
      border-color: grey;
    }
  }
`;

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
      <span className={isError ? 'error' : ''}>
        {isError === null ? description : isError ? errorMsg : ' '}
      </span>
    </FormInputContainer>
  );
};

const Button = styled.button`
  margin-left: 25px;

  height: 36px;
  background-color: var(--purple-light);
  padding: 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--purple);
  border: 1px solid var(--purple);
  font-size: 15px;
  font-weight: 700;
  line-height: 18px;
  white-space: nowrap;
  transition: 300ms ease-in-out;
  opacity: ${(props) => (props.disabled ? 0.5 : 1)};

  &:hover {
    background-color: var(--purple-medium);
    color: var(--purple);
  }

  &:active {
    background-color: var(--purple);
    color: var(--purple-light);
  }
`;

const CheckButton = ({ text, disabled, onClick }) => {
  return (
    <Button onClick={onClick} disabled={disabled}>
      {text}
    </Button>
  );
};

const SignUp = () => {
  const [userId, setUserId] = useState('');
  const [userIdErr, setUserIdErr] = useState(null); // id 에러(유효성)
  const [idIsChecked, setIdChecked] = useState(false); // id 중복검사+코드전송

  const [verificationCode, setVerificationCode] = useState('');
  const [verificationCodeErr, setVerificationCodeErr] = useState(null);

  const [nickName, setNickName] = useState('');
  const [nickNameErr, setNickNameErr] = useState(null);

  const [password, setPassword] = useState('');
  const [passwordErr, setPasswordErr] = useState(null);

  const [passwordCheck, setPasswordCheck] = useState('');
  const [passwordCheckErr, setPasswordCheckErr] = useState(null);

  // 아이디(이메일) 유효성 검사
  const onIdValidation = () => {
    const email = new RegExp('[a-z0-9]+@[a-z]+.[a-z]{2,3}');
    setUserIdErr(!email.test(userId));
  };
  // 인증코드 확인 -> ?
  const onVerificationValidation = () => {
    console.log('Validation Check');
  };
  // 닉네임 유효성(영문 숫자만) 검사
  const onNickNameValidation = () => {
    setNickNameErr(!(nickName.length > 1));
  };

  const onPasswordValidation = () => {
    const regex =
      /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
    setPasswordErr(!regex.test(password));
  };

  const onPasswordCheckValidation = () => {
    console.log(password, passwordCheck);
    setPasswordCheckErr(password !== passwordCheck);
  };

  // 아이디 중복확인, 이메일 전송
  const onCheckIdBtn = () => {
    if (!userIdErr) {
      axios
        .post(`/members/signup/email-send`, {
          email: userId,
        })
        .then((res) => {
          console.log(res.data.data);
          setIdChecked(true);
        })
        .catch((err) => console.error(err));
    }
  };

  // 인증코드 확인
  const onCheckVerificationBtn = () => {
    axios
      .post(`/members/signup/email-auth`, {
        email: userId,
        code: verificationCode,
      })
      .then((res) => {
        console.log(res.data.data);
        setVerificationCodeErr(false);
      })
      .catch((err) => {
        console.error(err);
        setVerificationCodeErr(true);
      });
  };

  // 닉네임 중복 확인
  const onCheckNickNameBtn = () => {
    axios
      .post(`/members/signup/verify-name`, {
        name: nickName,
      })
      .then((res) => {
        console.log('사용 가능한 닉네임 입니다');
        setNickNameErr(false);
      })
      .catch((err) => {
        console.warn(err.response.data.message);
        setNickNameErr(true);
      });
  };

  // 밸류 값, 유효성 통과 여부

  return (
    <Container>
      <h1>회원가입</h1>
      <div className="form-grid">
        <div className="leftside">
          <label htmlFor="user-id">아이디</label>
          <div className="input-wrapper">
            <FormInput
              id="user-id"
              description="아이디로 사용될 이메일을 입력해주세요"
              errorMsg="이메일 주소를 다시 확인해주세요"
              value={userId}
              onChange={setUserId}
              onBlur={onIdValidation}
              isError={userIdErr}
            />
            <CheckButton text="인증하기" onClick={onCheckIdBtn} />
          </div>
          <label htmlFor="verification-code">인증코드</label>
          <div className="input-wrapper">
            <FormInput
              id="verification-code"
              description={
                idIsChecked
                  ? '이메일로 전송된 코드를 입력해주세요'
                  : '이메일 인증을 진행해주세요'
              }
              value={verificationCode}
              onChange={setVerificationCode}
              errorMsg="인증 코드가 일치하지 않습니다"
              isError={verificationCodeErr}
              onBlur={onVerificationValidation}
              disabled={!idIsChecked}
            />
            <CheckButton
              text="인증확인"
              onClick={onCheckVerificationBtn}
              disabled={!idIsChecked}
            />
          </div>
          <label htmlFor="nickname">닉네임</label>
          <div className="input-wrapper">
            <FormInput
              id="nickname"
              description="이름을 입력해주세요"
              value={nickName}
              onChange={setNickName}
              errorMsg="1자 이상 입력해주세요"
              isError={nickNameErr}
              onBlur={onNickNameValidation}
            />
            <CheckButton text="중복확인" onClick={onCheckNickNameBtn} />
          </div>
          <label htmlFor="password">비밀번호</label>
          <FormInput
            id="password"
            description="8글자 이상의 영문, 숫자, 특수문자 조합이어야 합니다"
            value={password}
            onChange={setPassword}
            errorMsg="영문, 숫자, 특수문자를 포함해 8자 이상 입력해주세요"
            isError={passwordErr}
            onBlur={onPasswordValidation}
            type="password"
          />
          <label htmlFor="password-check">비밀번호 확인</label>
          <FormInput
            id="password-check"
            description="비밀번호를 다시 한 번 입력해주세요"
            value={passwordCheck}
            onChange={setPasswordCheck}
            errorMsg="비밀번호가 일치하지 않습니다"
            isError={passwordCheckErr}
            onBlur={onPasswordCheckValidation}
            type="password"
          />
          <label htmlFor="level">숙련도</label>
          <LevelSelect id="level" />
        </div>
        <div className="rightside">
          <div className="select-section">
            <label htmlFor="skill-stack">
              사용해보신 기술 스택을 선택해주세요
            </label>
            <SkillStackSelect id="skill-stack" />
          </div>
          <div className="select-section">
            <label htmlFor="interest">관심 분야를 선택해주세요</label>
            <InterestSelect id="interest" />
          </div>
          <div className="select-section github-link">
            <label htmlFor="github">
              깃허브<span>(선택)</span>
            </label>
            <MiniButton text="연동하기" />
          </div>
        </div>
      </div>
      <DefaultButton text="가입하기" className="submit-btn" />
    </Container>
  );
};

export default SignUp;
