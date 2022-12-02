import styled from 'styled-components';
import { useState } from 'react';
import axios from 'axios';
import { useSetRecoilState, useRecoilState } from 'recoil';
import {
  modalOpenState,
  searchPwEmailState,
  emailState,
  passwordState,
  currentUserState,
} from '../atom/atom';
import CloseButton from '../components/CloseButton';
import DefaultButton from '../components/DefaultButton';
import DefaultInput from '../components/DefaultInput';
import MiniButton from '../components/MiniButton';
import { notiError, notiSuccess, notiInfo } from '../assets/toast';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  height: 100%;
  width: 100%;
  inset: 0px;

  .pageblur {
    position: fixed;
    height: 100%;
    width: 100%;
    background-color: rgba(176, 176, 179, 0.8);
  }

  .login-container {
    display: flex;
    flex-direction: column;
    height: 640px;
    width: 500px;
    background-color: white;
    border-radius: 8px;
    border: none;
    border: 1px solid var(--purple);
    align-items: center;
    z-index: 1;
  }

  .close-button-container {
    display: flex;
    justify-content: flex-end;
    width: 100%;
    padding: 5px;
  }

  .login-button {
    height: 60px;
    width: 414px;
    margin-top: 50px;
  }

  .login-title {
    font-size: 40px;
    font-weight: 700;
    padding: 15px;
  }

  .form-container {
    padding: 10px;
  }

  .form-title {
    font-size: 20px;
    font-weight: 700;
  }

  .form-input {
    width: 400px;
    height: 45px;
    font-size: 15px;
    border: none;
    border-bottom: 1px solid var(--purple);

    &::placeholder {
      color: var(--purple);
    }

    &:focus,
    &:active {
      outline: none;
    }
  }

  .form-alert {
    color: var(--purple);
    font-size: 13px;
    padding-bottom: 10px;
    height: 3px;
  }

  .link-container {
    display: flex;
    justify-content: space-around;
    width: 360px;
    padding: 25px;
    font-size: 18px;
    font-weight: 600;
  }
  .link {
    color: #55acee;
  }

  .PW-button {
    color: #55acee;
    border: none;
    outline: none;
    background-color: white;
    font-size: 18px;
    font-weight: 600;
  }

  .search-pw-container {
    width: 413px;

    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  .search-input-container {
    width: 85%;
  }

  .search-btn {
    button {
      height: 100%;
    }
  }
`;

const Authbutton = styled.button`
  height: 60px;
  width: 60px;
  border-radius: 30px;
  background-color: #fff;
  border: none;
  margin: 0 60px 0 0;

  :last-child {
    margin-right: 0;
  }

  img {
    width: 100%;
    height: 100%;
  }
`;

const LogIn = ({ userMenu }) => {
  const setModalOpen = useSetRecoilState(modalOpenState);
  const [isSearchPw, setIsSearchPw] = useState(false);
  const [searchPwEmail, setSearchPwEmail] = useRecoilState(searchPwEmailState);
  const [noticeText, setNoticeText] = useState('');
  const [emailNotice, setEmailNotice] = useState('');
  const [email, setEmail] = useRecoilState(emailState);
  const [password, setPassword] = useRecoilState(passwordState);
  const setCurrentUser = useSetRecoilState(currentUserState);

  const closeLogIn = () => {
    setModalOpen(false);
  };

  const onSearchPwClick = () => {
    setIsSearchPw(!isSearchPw);
    setNoticeText('');
    setSearchPwEmail('');
  };

  const changeEmail = (e) => {
    setEmail(e.target.value);
  };

  const changePassword = (e) => {
    setPassword(e.target.value);
  };

  const enterPress = (e) => {
    if (e.key === 'Enter') {
      LogInPost();
    }
  };

  const onCheckEmail = (email, setNotice) => {
    const emailCheck =
      /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i;
    if (emailCheck.test(email) === false) {
      setNotice('올바른 이메일 형식이 아닙니다.');
      return false;
    } else {
      setNotice('');
      return true;
    }
  };

  const onResetPw = () => {
    if (onCheckEmail(searchPwEmail, setNoticeText)) {
      axios
        .post(`/members/find-password`, { email: searchPwEmail })
        .then(
          // 요청 후 서버에 이메일이 존재하면 실행
          (res) => {
            setNoticeText(res.data.data);
          },
        )
        .catch((ex) => {
          // 요청 후 서버에 이메일 존재하지 않아 404에러 발생시 실행
          if (ex.response && ex.response.status === 404) {
            setNoticeText(ex.response.data.message);
          }
        });
    } else {
      notiError('올바른 이메일 형식이 아닙니다.\n다시 입력해주세요.');
    }
  };

  const LogInPost = () => {
    if (onCheckEmail(email, setEmailNotice)) {
      axios
        .post(`/members/login`, { email, password })
        .then((res) => {
          localStorage.setItem('Authorization', res.headers.authorization);
          localStorage.setItem('Refresh', res.headers.refresh);
          setCurrentUser({
            memberId: Number(res.headers.memberid),
            isLogIn: true,
          });
          setEmail('');
          setPassword('');
          notiSuccess('로그인 완료 되었습니다.');
          closeLogIn();
        })
        .catch((ex) => {
          // 요청 후 서버에 이메일 존재하지 않아 404에러 발생시 실행
          if (ex.response && ex.response.status === 404) {
            notiError(ex.response.data.message);
          }
        });
    }
  };

  const onGithubLogin = () => {
    const githubPopup = window.open(
      process.env.REACT_APP_OAUTH_GITHUB_URL,
      '깃허브 인증창',
      'width=600px,height=500px,scrollbars=yes',
    );
    githubPopup.addEventListener('unload', () => {
      const memberId = window.localStorage.getItem('memberId');
      const Authorization = window.localStorage.getItem('Authorization');
      const githubURL = window.localStorage.getItem('githubURL');

      if (Authorization) {
        setCurrentUser({ memberId: Number(memberId), isLogIn: true });
        notiSuccess('로그인 되었습니다!');
      } else if (githubURL) {
        notiError(
          '등록된 깃허브 계정이 아닙니다! 회원가입 후 깃허브 계정을 연동해주세요',
        );
      }
    });
  };

  return (
    <Container>
      <div className="pageblur" ref={userMenu} />
      <div className="login-container">
        <span className="close-button-container">
          <CloseButton onClick={closeLogIn} />
        </span>
        <span className="login-title">로그인</span>
        <form>
          <div className="form-container">
            <div className="form-title">이메일</div>
            <input
              className="form-input"
              onChange={changeEmail}
              value={email}
              placeholder={'이메일을 입력해주세요.'}
              onKeyPress={enterPress}
            />
            {emailNotice ? (
              <div className="form-alert">{emailNotice}</div>
            ) : (
              <div className="form-alert" />
            )}
          </div>
          <div className="form-container">
            <div className="form-title">비밀번호</div>
            <input
              className="form-input"
              onChange={changePassword}
              value={password}
              placeholder={'패스워드를 입력해주세요.'}
              type={'password'}
              onKeyPress={enterPress}
            />
          </div>
        </form>
        <DefaultButton
          className="login-button"
          text="로그인"
          onClick={LogInPost}
          type="submit"
        />
        <div className="link-container">
          {isSearchPw ? (
            <button className="link PW-button" onClick={onSearchPwClick}>
              소셜로그인
            </button>
          ) : (
            <button className="link PW-button" onClick={onSearchPwClick}>
              비밀번호 찾기
            </button>
          )}
          <a href="/signup" className="link">
            회원가입 하기
          </a>
        </div>
        {isSearchPw ? (
          <>
            <div className="search-pw-container">
              <div className="search-input-container">
                <div className="search-input">
                  <DefaultInput
                    placeholder={'이메일을 입력해주세요.'}
                    value={searchPwEmail}
                    onChange={setSearchPwEmail}
                    onblur={onCheckEmail}
                  />
                </div>
              </div>
              <div className="search-btn">
                <MiniButton text="찾기" onClick={onResetPw} />
              </div>
            </div>
            {noticeText ? (
              <div className="form-alert">{noticeText}</div>
            ) : (
              <div className="form-alert" />
            )}
          </>
        ) : (
          <div className="image-container">
            <Authbutton onClick={() => notiInfo('준비 중인 기능입니다!')}>
              <img
                src={require('../images/google login.png')}
                alt="구글 로그인"
              ></img>
            </Authbutton>
            <Authbutton onClick={onGithubLogin}>
              <img
                src={require('../images/github login.png')}
                alt="깃허브 로그인"
              ></img>
            </Authbutton>
            <Authbutton onClick={() => notiInfo('준비 중인 기능입니다!')}>
              <img
                src={require('../images/kakao login.png')}
                alt="카카오톡 로그인"
              ></img>
            </Authbutton>
          </div>
        )}
      </div>
    </Container>
  );
};

export default LogIn;
