import '@vaadin/tooltip/src/vaadin-tooltip.js';
import '@vaadin/polymer-legacy-adapter/style-modules.js';
import '@vaadin/app-layout/src/vaadin-drawer-toggle.js';
import '@vaadin/side-nav/src/vaadin-side-nav.js';
import '@vaadin/icon/src/vaadin-icon.js';
import '@vaadin/side-nav/src/vaadin-side-nav-item.js';
import '@vaadin/icons/vaadin-iconset.js';
import '@vaadin/button/src/vaadin-button.js';
import 'Frontend/generated/jar-resources/buttonFunctions.js';
import '@vaadin/scroller/src/vaadin-scroller.js';
import '@vaadin/vertical-layout/src/vaadin-vertical-layout.js';
import '@vaadin/app-layout/src/vaadin-app-layout.js';
import '@vaadin/common-frontend/ConnectionIndicator.js';
import '@vaadin/vaadin-lumo-styles/color-global.js';
import '@vaadin/vaadin-lumo-styles/typography-global.js';
import '@vaadin/vaadin-lumo-styles/sizing.js';
import '@vaadin/vaadin-lumo-styles/spacing.js';
import '@vaadin/vaadin-lumo-styles/style.js';
import '@vaadin/vaadin-lumo-styles/vaadin-iconset.js';

const loadOnDemand = (key) => {
  const pending = [];
  if (key === '7d22c4c9efa6a450c98c5e7cadc8d2bcc1a090fcf8769dce4e13a667a473028b') {
    pending.push(import('./chunks/chunk-616d40436d37d85d090e8d6e7d78cedd689ae49d50b6cac230e964dc9df95eb9.js'));
  }
  if (key === 'c7eaaec6d463c9053a548e0a53746774efb5a9e691df14d0f5ea6034ec6b60c9') {
    pending.push(import('./chunks/chunk-3fc6d1085d3e6fae39194f32c6673c08b98e7724b3a1fc133c54228d58d4fbdb.js'));
  }
  if (key === '475b6ccf782fcadff12e8b5c0947498b733c8dda2c5c111f8ac5b03f6dad26ea') {
    pending.push(import('./chunks/chunk-69365e5d0da776276dbc227ba65218b33f6a9b9cd49f4d6bb9e5eb071001b66f.js'));
  }
  if (key === 'c2fc3650868b55008b245a77b9cf23279d47b2a83f1a9635806cf38509e5f7e4') {
    pending.push(import('./chunks/chunk-bf652d3125f4c9665e45892823a849d5653b5f39e7721fa4bc1ccf9624d145b1.js'));
  }
  return Promise.all(pending);
}

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};
window.Vaadin.Flow.loadOnDemand = loadOnDemand;