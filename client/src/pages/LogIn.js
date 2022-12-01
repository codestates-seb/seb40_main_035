import styled from 'styled-components';
import DefaultButton from '../components/DefaultButton';
import { useSetRecoilState, useRecoilState } from 'recoil';
import { modalOpenState, searchPwEmailState } from '../atom/atom';
import CloseButton from '../components/CloseButton';
import { useState } from 'react';
import DefaultInput from '../components/DefaultInput';
import MiniButton from '../components/MiniButton';
import axios from 'axios';
import { notiError } from '../assets/toast';

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: rgba(176, 176, 179, 0.8);
  inset: 0px;
  z-index: 2;

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
    z-index: 3;
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
    margin-top: 40px;
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

    &:focus,
    &:active {
      outline: none;
    }
  }

  .form-alert {
    color: var(--purple);
    font-size: 13px;
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

const Authbutton = styled.a`
  margin: 30px;

  img {
    width: 60px;
    height: 60px;
  }
`;

const LogIn = ({ userMenu }) => {
  const setModalOpen = useSetRecoilState(modalOpenState);
  const [isSearchPw, setIsSearchPw] = useState(false);
  const [searchPwEmail, setSearchPwEmail] = useRecoilState(searchPwEmailState);
  const [noticeText, setNoticeText] = useState('');
  const [isNotice, setIsNotice] = useState(false);

  const closeLogIn = () => {
    setModalOpen(false);
  };

  const onSearchPwClick = () => {
    setIsSearchPw(!isSearchPw);
    setIsNotice(false);
    setNoticeText('');
    setSearchPwEmail('');
  };

  const onCheckEmail = () => {
    let emailCheck =
      /[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]$/i;
    if (emailCheck.test(searchPwEmail) === false) {
      setIsNotice(true);
      setNoticeText('올바른 이메일 형식이 아닙니다.');
      return false;
    } else {
      setIsNotice(false);
      return true;
    }
  };

  const onResetPw = () => {
    if (onCheckEmail(searchPwEmail)) {
      axios
        .post(`/members/find-password`, { email: searchPwEmail })
        .then(
          // 요청 후 서버에 이메일이 존재하면 실행
          (res) => {
            setIsNotice(true);
            setNoticeText(res.data.data);
          },
        )
        .catch((ex) => {
          // 요청 후 서버에 이메일 존재하지 않아 404에러 발생시 실행
          if (ex.response && ex.response.status === 404) {
            setIsNotice(true);
            setNoticeText(ex.response.data.message);
          }
        });
    } else {
      notiError('올바른 이메일 형식이 아닙니다.\n다시 입력해주세요.');
    }
  };

  return (
    <Container>
      <div className="login-container" ref={userMenu}>
        <span className="close-button-container">
          <CloseButton onClick={closeLogIn} />
        </span>
        <span className="login-title">로그인</span>
        <form>
          <div className="form-container">
            <div className="form-title">이메일</div>
            <input className="form-input" />
            <div className="form-alert">가입되지 않은 이메일 입니다.</div>
          </div>
          <div className="form-container">
            <div className="form-title">비밀번호</div>
            <input className="form-input" />
            <div className="form-alert">비밀번호를 확인해 주세요.</div>
          </div>
        </form>
        <DefaultButton className="login-button" text="로그인" />
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
                    placeholder={'이메일을 입력해주세요'}
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
            {isNotice ? <div className="form-alert">{noticeText}</div> : ''}
          </>
        ) : (
          <div className="image-container">
            <Authbutton href="">
              <img
                src={require('../images/google login.png')}
                alt="구글로그인"
              ></img>
            </Authbutton>
            <Authbutton href="">
              <img
                src={require('../images/github login.png')}
                alt="깃허브로그인"
              ></img>
            </Authbutton>
            <Authbutton href="">
              <img
                src={require('../images/kakao login.png')}
                alt="카카오톡로그인"
              ></img>
            </Authbutton>
          </div>
        )}
      </div>
    </Container>
  );
};

export default LogIn;
