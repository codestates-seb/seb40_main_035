import { createGlobalStyle } from 'styled-components';
import Variables from './GlobalVariable';

const GlobalStyle = createGlobalStyle`
  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0; 
    font-family: 'Noto Sans KR', sans-serif;
  }
  ::-webkit-scrollbar {
    display: none;
  }

:root {
    ${Variables};
  }

  ul, ol, li{
    list-style-type: none;
  }

  a, a:hover, a:active, a:visited {
    text-decoration: none;
  }

  button {
    cursor: pointer;
  }
`;

export default GlobalStyle;
