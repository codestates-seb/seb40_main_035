import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { BiMoon } from 'react-icons/bi';
const HeaderContainer = styled.header`
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  position: fixed;

  .header-container {
    width: 1864px;
    padding: 10px;
    max-width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    background-color: var(--purple);
  }
  a {
    text-decoration-line: none;
    color: var(--purple-light);
  }
  .gnb-container-left {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .gnb-container-right {
    display: flex;
    align-items: center;
    padding-right: 142px;
  }
  .logo {
    padding-right: 50px;
    padding-left: 170px;
  }
  .gnb {
    display: flex;

    li {
      list-style: none;
      color: var(--purple-light);
      font-weight: 700;
      margin-right: 28px;
      font-size: 15px;
    }
  }
`;

const Header = () => {
  return (
    <HeaderContainer>
      <div className="header-container">
        <div className="gnb-container-left">
          <h1 className="logo">
            <Link to="/">
              <span>삼삼오오</span>
            </Link>
          </h1>
          <ul className="gnb">
            <li>
              <Link to="/about">
                <span>사이트 소개</span>
              </Link>
            </li>
            <li>
              <Link to="/">
                <span>프로젝트 </span>
              </Link>
            </li>
          </ul>
        </div>
        <div className="gnb-container-right">
          <ul className="gnb">
            <li>
              <Link to="/write">
                <span>글쓰기</span>
              </Link>
            </li>
            <li>
              <Link to="/login">
                <span>로그인 </span>
              </Link>
            </li>
            <li>
              <Link to="/signup">
                <span>회원가입</span>
              </Link>
            </li>
            <li>
              <BiMoon className="moon-icon" />
            </li>
          </ul>
        </div>
      </div>
    </HeaderContainer>
  );
};

export default Header;
