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
      setNotice('????????? ????????? ????????? ????????????.');
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
          // ?????? ??? ????????? ???????????? ???????????? ??????
          (res) => {
            setNoticeText(res.data.data);
          },
        )
        .catch((ex) => {
          // ?????? ??? ????????? ????????? ???????????? ?????? 404?????? ????????? ??????
          if (ex.response && ex.response.status === 404) {
            setNoticeText(ex.response.data.message);
          }
        });
    } else {
      notiError('????????? ????????? ????????? ????????????.\n?????? ??????????????????.');
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
          notiSuccess('????????? ???????????????.');
          closeLogIn();
        })
        .catch((ex) => {
          // ?????? ??? ????????? ????????? ???????????? ?????? 404?????? ????????? ??????
          if (ex.response && ex.response.status === 404) {
            notiError(ex.response.data.message);
          }
        });
    }
  };

  const onGithubLogin = () => {
    const githubPopup = window.open(
      process.env.REACT_APP_OAUTH_GITHUB_URL,
      '????????? ?????????',
      'width=600px,height=500px,scrollbars=yes',
    );
    githubPopup.addEventListener('unload', () => {
      const memberId = window.localStorage.getItem('memberId');
      const Authorization = window.localStorage.getItem('Authorization');
      const githubURL = window.localStorage.getItem('githubURL');

      if (Authorization) {
        setCurrentUser({ memberId: Number(memberId), isLogIn: true });
        closeLogIn();
        notiSuccess('????????? ???????????????!');
      } else if (githubURL) {
        notiError(
          '????????? ????????? ????????? ????????????! ????????? ??? ????????? ????????? ??????????????????.',
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
        <span className="login-title">?????????</span>
        <form>
          <div className="form-container">
            <div className="form-title">?????????</div>
            <input
              className="form-input"
              onChange={changeEmail}
              value={email}
              placeholder={'???????????? ??????????????????.'}
              onKeyPress={enterPress}
            />
            {emailNotice ? (
              <div className="form-alert">{emailNotice}</div>
            ) : (
              <div className="form-alert" />
            )}
          </div>
          <div className="form-container">
            <div className="form-title">????????????</div>
            <input
              className="form-input"
              onChange={changePassword}
              value={password}
              placeholder={'??????????????? ??????????????????.'}
              type={'password'}
              onKeyPress={enterPress}
            />
          </div>
        </form>
        <DefaultButton
          className="login-button"
          text="?????????"
          onClick={LogInPost}
          type="submit"
        />
        <div className="link-container">
          {isSearchPw ? (
            <button className="link PW-button" onClick={onSearchPwClick}>
              ???????????????
            </button>
          ) : (
            <button className="link PW-button" onClick={onSearchPwClick}>
              ???????????? ??????
            </button>
          )}
          <a href="/signup" className="link">
            ???????????? ??????
          </a>
        </div>
        {isSearchPw ? (
          <>
            <div className="search-pw-container">
              <div className="search-input-container">
                <div className="search-input">
                  <DefaultInput
                    placeholder={'???????????? ??????????????????.'}
                    value={searchPwEmail}
                    onChange={setSearchPwEmail}
                    onblur={onCheckEmail}
                  />
                </div>
              </div>
              <div className="search-btn">
                <MiniButton text="??????" onClick={onResetPw} />
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
            <Authbutton onClick={() => notiInfo('?????? ?????? ???????????????!')}>
              <img
                src={require('../images/google login.png')}
                alt="?????? ?????????"
              ></img>
            </Authbutton>
            <Authbutton onClick={onGithubLogin}>
              <img
                src={require('../images/github login.png')}
                alt="????????? ?????????"
              ></img>
            </Authbutton>
            <Authbutton onClick={() => notiInfo('?????? ?????? ???????????????!')}>
              <img
                src={require('../images/kakao login.png')}
                alt="???????????? ?????????"
              ></img>
            </Authbutton>
          </div>
        )}
      </div>
    </Container>
  );
};

export default LogIn;
