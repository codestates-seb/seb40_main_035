import styled from 'styled-components';
import DefaultButton from '../components/DefaultButton';
import { AiOutlineClose } from 'react-icons/ai';
import { useSetRecoilState } from 'recoil';
import { modalOpenState } from '../atom/atom';

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

  .close-button {
    cursor: pointer;
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

    &:active {
      border-color: var(--purple);
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

  const closeLogIn = () => {
    setModalOpen(false);
  };
  return (
    <Container>
      <div className="login-container" ref={userMenu}>
        <div className="close-button-container">
          <AiOutlineClose
            className="close-button"
            size="24px"
            onClick={closeLogIn}
            type="button"
          />
        </div>
        <span className="login-title">로그인</span>
        <form>
          <div className="form-container">
            <div className="form-title">이메일</div>
            <input className="form-input"></input>
            <div className="form-alert">가입되지 않은 이메일 입니다.</div>
          </div>
          <div className="form-container">
            <div className="form-title">비밀번호</div>
            <input className="form-input"></input>
            <div className="form-alert">비밀번호를 확인해 주세요.</div>
          </div>
        </form>
        <DefaultButton className="login-button" text="로그인" />
        <div className="link-container">
          <a href="" className="link">
            비밀번호 찾기
          </a>
          <a href="" className="link">
            회원가입 하기
          </a>
        </div>
        <div className="image-container">
          <Authbutton href="">
            <img src={require('../images/google login.png')} alt=""></img>
          </Authbutton>
          <Authbutton href="">
            <img src={require('../images/github login.png')} alt=""></img>
          </Authbutton>
          <Authbutton href="">
            <img src={require('../images/kakao login.png')} alt=""></img>
          </Authbutton>
        </div>
      </div>
    </Container>
  );
};

export default LogIn;
